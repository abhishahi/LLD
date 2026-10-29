package com.splitwise.service;

import com.splitwise.api.ApiDtos.BalanceResponse;
import com.splitwise.api.ApiDtos.MyBalanceResponse;
import com.splitwise.api.ApiDtos.SettlementExecutionResponse;
import com.splitwise.api.ApiDtos.SettlementPlanResponse;
import com.splitwise.api.ApiDtos.TransferResponse;
import com.splitwise.domain.Expense;
import com.splitwise.domain.ExpenseGroup;
import com.splitwise.domain.Settlement;
import com.splitwise.domain.User;
import com.splitwise.repository.ExpenseRepository;
import com.splitwise.repository.SettlementRepository;
import com.splitwise.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SettlementService {

    private static final BigDecimal ZERO = new BigDecimal("0.00");

    private final ExpenseRepository expenses;
    private final SettlementRepository settlements;
    private final CurrentUserService currentUserService;
    private final GroupService groupService;
    private final UserRepository users;

    public SettlementService(ExpenseRepository expenses, SettlementRepository settlements,
                             CurrentUserService currentUserService, GroupService groupService, UserRepository users) {
        this.expenses = expenses;
        this.settlements = settlements;
        this.currentUserService = currentUserService;
        this.groupService = groupService;
        this.users = users;
    }

    @Transactional(readOnly = true)
    public MyBalanceResponse myBalance(Authentication authentication) {
        User user = currentUserService.get(authentication);
        Map<Long, BigDecimal> balances = calculateBalances(expenses.findAllWithParticipants(), settlements.findAllWithUsers());
        BigDecimal net = balances.getOrDefault(user.getId(), ZERO);
        return new MyBalanceResponse(net, net.signum() < 0 ? net.abs() : ZERO, net.signum() > 0 ? net : ZERO);
    }

    @Transactional(readOnly = true)
    public List<BalanceResponse> groupBalances(Authentication authentication, Long groupId) {
        ExpenseGroup group = groupService.requireMember(authentication, groupId);
        Map<Long, BigDecimal> balances = calculateGroupBalances(group);
        return group.getMembers().stream()
                .sorted(Comparator.comparing(User::getId))
                .map(user -> new BalanceResponse(
                        user.getId(),
                        user.getName(),
                        balances.getOrDefault(user.getId(), ZERO)))
                .toList();
    }

    @Transactional(readOnly = true)
    public SettlementPlanResponse preview(Authentication authentication, Long groupId) {
        User actor = currentUserService.get(authentication);
        List<Transfer> plan;
        if (groupId == null) {
            Map<Long, BigDecimal> balances = calculateBalances(expenses.findByGroupIsNull(), settlements.findByGroupIsNull());
            plan = planForUser(balances, actor.getId());
        } else {
            ExpenseGroup group = groupService.requireMember(authentication, groupId);
            plan = planForEveryone(calculateGroupBalances(group));
        }
        return new SettlementPlanResponse(
                groupId,
                plan.stream().map(Transfer::response).toList());
    }

    @Transactional
    public SettlementExecutionResponse execute(Authentication authentication, Long groupId) {
        User actor = currentUserService.get(authentication);
        ExpenseGroup group = groupId == null ? null : groupService.requireMember(authentication, groupId);
        Map<Long, BigDecimal> balances = group == null
                ? calculateBalances(expenses.findByGroupIsNull(), settlements.findByGroupIsNull())
                : calculateGroupBalances(group);
        List<Transfer> plan = group == null
                ? planForUser(balances, actor.getId())
                : planForEveryone(balances);

        List<Settlement> saved = plan.stream()
                .map(transfer -> settlements.save(new Settlement(
                        transfer.from(), transfer.to(), group, transfer.amount())))
                .toList();
        return new SettlementExecutionResponse(
                groupId,
                saved.stream().map(TransferResponse::from).toList(),
                saved.isEmpty() ? "No outstanding balances to settle" : "Settlement transactions recorded");
    }

    private Map<Long, BigDecimal> calculateGroupBalances(ExpenseGroup group) {
        return calculateBalances(
                expenses.findByGroup_Id(group.getId()),
                settlements.findByGroup_Id(group.getId()));
    }

    private Map<Long, BigDecimal> calculateBalances(List<Expense> expenseList, List<Settlement> settlementList) {
        Map<Long, BigDecimal> balances = new LinkedHashMap<>();
        for (Expense expense : expenseList) {
            expense.getPayments().forEach(payment ->
                    balances.merge(payment.getUser().getId(), payment.getAmount(), BigDecimal::add));
            expense.getShares().forEach(share ->
                    balances.merge(share.getUser().getId(), share.getAmount().negate(), BigDecimal::add));
        }
        for (Settlement settlement : settlementList) {
            balances.merge(settlement.getFromUser().getId(), settlement.getAmount(), BigDecimal::add);
            balances.merge(settlement.getToUser().getId(), settlement.getAmount().negate(), BigDecimal::add);
        }
        balances.replaceAll((userId, amount) -> amount.setScale(2));
        return balances;
    }

    private List<Transfer> planForUser(Map<Long, BigDecimal> balances, Long userId) {
        BigDecimal userBalance = balances.getOrDefault(userId, ZERO);
        if (userBalance.signum() == 0) {
            return List.of();
        }

        List<Transfer> transfers = new ArrayList<>();
        if (userBalance.signum() < 0) {
            BigDecimal remaining = userBalance.abs();
            for (Map.Entry<Long, BigDecimal> entry : sortedBalances(balances).entrySet()) {
                if (entry.getValue().signum() <= 0 || entry.getKey().equals(userId)) {
                    continue;
                }
                BigDecimal amount = remaining.min(entry.getValue());
                transfers.add(transfer(userId, entry.getKey(), amount));
                remaining = remaining.subtract(amount);
                if (remaining.signum() == 0) {
                    break;
                }
            }
        } else {
            BigDecimal remaining = userBalance;
            for (Map.Entry<Long, BigDecimal> entry : sortedBalances(balances).entrySet()) {
                if (entry.getValue().signum() >= 0 || entry.getKey().equals(userId)) {
                    continue;
                }
                BigDecimal amount = remaining.min(entry.getValue().abs());
                transfers.add(transfer(entry.getKey(), userId, amount));
                remaining = remaining.subtract(amount);
                if (remaining.signum() == 0) {
                    break;
                }
            }
        }
        return transfers;
    }

    private List<Transfer> planForEveryone(Map<Long, BigDecimal> balances) {
        TreeMap<Long, BigDecimal> debtors = new TreeMap<>();
        TreeMap<Long, BigDecimal> creditors = new TreeMap<>();
        balances.forEach((userId, balance) -> {
            if (balance.signum() < 0) {
                debtors.put(userId, balance.abs());
            } else if (balance.signum() > 0) {
                creditors.put(userId, balance);
            }
        });

        List<Transfer> transfers = new ArrayList<>();
        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            Map.Entry<Long, BigDecimal> debtor = debtors.firstEntry();
            Map.Entry<Long, BigDecimal> creditor = creditors.firstEntry();
            BigDecimal amount = debtor.getValue().min(creditor.getValue());
            transfers.add(transfer(debtor.getKey(), creditor.getKey(), amount));

            BigDecimal debtorRemaining = debtor.getValue().subtract(amount);
            BigDecimal creditorRemaining = creditor.getValue().subtract(amount);
            if (debtorRemaining.signum() == 0) {
                debtors.pollFirstEntry();
            } else {
                debtors.put(debtor.getKey(), debtorRemaining);
            }
            if (creditorRemaining.signum() == 0) {
                creditors.pollFirstEntry();
            } else {
                creditors.put(creditor.getKey(), creditorRemaining);
            }
        }
        return transfers;
    }

    private TreeMap<Long, BigDecimal> sortedBalances(Map<Long, BigDecimal> balances) {
        TreeMap<Long, BigDecimal> sorted = new TreeMap<>();
        sorted.putAll(balances);
        return sorted;
    }

    private Transfer transfer(Long fromId, Long toId, BigDecimal amount) {
        User from = users.findById(fromId).orElseThrow();
        User to = users.findById(toId).orElseThrow();
        return new Transfer(from, to, amount);
    }

    private record Transfer(User from, User to, BigDecimal amount) {
        TransferResponse response() {
            return new TransferResponse(
                    from.getId(), from.getName(), to.getId(), to.getName(), amount);
        }
    }
}
