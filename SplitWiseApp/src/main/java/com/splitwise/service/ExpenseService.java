package com.splitwise.service;

import com.splitwise.api.ApiDtos.CreateExpenseRequest;
import com.splitwise.api.ApiDtos.ExpenseResponse;
import com.splitwise.api.ApiDtos.PaymentInput;
import com.splitwise.api.ApiDtos.ShareInput;
import com.splitwise.domain.Expense;
import com.splitwise.domain.ExpenseGroup;
import com.splitwise.domain.SplitType;
import com.splitwise.domain.User;
import com.splitwise.repository.ExpenseRepository;
import com.splitwise.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ExpenseService {

    private final ExpenseRepository expenses;
    private final UserRepository users;
    private final CurrentUserService currentUserService;
    private final GroupService groupService;

    public ExpenseService(ExpenseRepository expenses, UserRepository users,
                          CurrentUserService currentUserService, GroupService groupService) {
        this.expenses = expenses;
        this.users = users;
        this.currentUserService = currentUserService;
        this.groupService = groupService;
    }

    @Transactional
    public ExpenseResponse create(Authentication authentication, CreateExpenseRequest request) {
        User creator = currentUserService.get(authentication);
        BigDecimal total = money(request.amount());
        ExpenseGroup group = request.groupId() == null
                ? null
                : groupService.requireMember(authentication, request.groupId());
        Map<Long, User> participants = loadParticipants(request, group);

        List<BigDecimal> payments = request.payments().stream().map(PaymentInput::amount).map(this::money).toList();
        requireUniqueIds(request.payments().stream().map(PaymentInput::userId).toList(), "Payer");
        if (sum(payments).compareTo(total) != 0) {
            throw new ResponseStatusException(BAD_REQUEST, "Payer amounts must add up to the expense total");
        }

        Map<Long, BigDecimal> shares = calculateShares(request, total);
        Expense expense = new Expense(total, request.description(), request.splitType(), creator, group);
        request.payments().forEach(payment ->
                expense.addPayment(participants.get(payment.userId()), money(payment.amount())));
        shares.forEach((userId, amount) -> expense.addShare(participants.get(userId), amount));
        return ExpenseResponse.from(expenses.save(expense));
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> history(Authentication authentication) {
        User user = currentUserService.get(authentication);
        return expenses.findInvolvedByUserId(user.getId()).stream().map(ExpenseResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> groupHistory(Authentication authentication, Long groupId) {
        groupService.requireMember(authentication, groupId);
        return expenses.findByGroupId(groupId).stream().map(ExpenseResponse::from).toList();
    }

    private Map<Long, User> loadParticipants(CreateExpenseRequest request, ExpenseGroup group) {
        List<Long> ids = new ArrayList<>();
        request.payments().forEach(payment -> ids.add(payment.userId()));
        request.shares().forEach(share -> ids.add(share.userId()));
        Map<Long, User> participants = new LinkedHashMap<>();
        for (Long id : ids) {
            User user = users.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Participant user not found: " + id));
            if (group != null && !group.hasMember(id)) {
                throw new ResponseStatusException(BAD_REQUEST, "Every expense participant must be a group member");
            }
            participants.put(id, user);
        }
        return participants;
    }

    private Map<Long, BigDecimal> calculateShares(CreateExpenseRequest request, BigDecimal total) {
        Map<Long, BigDecimal> result = new LinkedHashMap<>();
        List<ShareInput> inputs = request.shares();
        requireUniqueIds(inputs.stream().map(ShareInput::userId).toList(), "Share participant");

        if (request.splitType() == SplitType.EQUAL) {
            if (inputs.stream().anyMatch(share -> share.value() != null)) {
                throw new ResponseStatusException(BAD_REQUEST, "EQUAL splits must not include share values");
            }
            BigDecimal base = total.divide(BigDecimal.valueOf(inputs.size()), 2, RoundingMode.DOWN);
            BigDecimal allocated = BigDecimal.ZERO.setScale(2);
            for (int i = 0; i < inputs.size(); i++) {
                BigDecimal share = i == inputs.size() - 1 ? total.subtract(allocated) : base;
                result.put(inputs.get(i).userId(), share);
                allocated = allocated.add(share);
            }
            return result;
        }

        if (inputs.stream().anyMatch(share -> share.value() == null || share.value().signum() <= 0)) {
            throw new ResponseStatusException(BAD_REQUEST, request.splitType() + " splits require a positive value for each participant");
        }
        if (request.splitType() == SplitType.EXACT) {
            for (ShareInput input : inputs) {
                result.put(input.userId(), money(input.value()));
            }
            if (sum(new ArrayList<>(result.values())).compareTo(total) != 0) {
                throw new ResponseStatusException(BAD_REQUEST, "Exact shares must add up to the expense total");
            }
            return result;
        }

        BigDecimal percentageTotal = inputs.stream().map(ShareInput::value).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (percentageTotal.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new ResponseStatusException(BAD_REQUEST, "Percent shares must add up to 100");
        }
        BigDecimal totalCents = total.movePointRight(2);
        BigDecimal allocatedCents = BigDecimal.ZERO;
        Map<Long, BigDecimal> fractionalCents = new LinkedHashMap<>();
        for (ShareInput input : inputs) {
            BigDecimal exactCents = totalCents.multiply(input.value()).divide(BigDecimal.valueOf(100));
            BigDecimal wholeCents = exactCents.setScale(0, RoundingMode.DOWN);
            result.put(input.userId(), wholeCents.movePointLeft(2).setScale(2));
            allocatedCents = allocatedCents.add(wholeCents);
            fractionalCents.put(input.userId(), exactCents.subtract(wholeCents));
        }

        List<Long> remainderOrder = inputs.stream()
                .map(ShareInput::userId)
                .sorted(Comparator.comparing(fractionalCents::get).reversed())
                .toList();
        int remainingCents = totalCents.subtract(allocatedCents).intValueExact();
        for (int i = 0; i < remainingCents; i++) {
            Long userId = remainderOrder.get(i);
            result.put(userId, result.get(userId).add(new BigDecimal("0.01")));
        }
        return result;
    }

    private BigDecimal money(BigDecimal amount) {
        try {
            if (amount.stripTrailingZeros().scale() > 2) {
                throw new ResponseStatusException(BAD_REQUEST, "Money amounts may have at most two decimal places");
            }
            return amount.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid money amount");
        }
    }

    private BigDecimal sum(List<BigDecimal> amounts) {
        return amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void requireUniqueIds(List<Long> ids, String label) {
        if (new HashSet<>(ids).size() != ids.size()) {
            throw new ResponseStatusException(BAD_REQUEST, label + " IDs must be unique");
        }
    }
}
