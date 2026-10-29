package com.splitwise.api;

import com.splitwise.domain.Expense;
import com.splitwise.domain.ExpenseGroup;
import com.splitwise.domain.ExpensePayment;
import com.splitwise.domain.ExpenseShare;
import com.splitwise.domain.Settlement;
import com.splitwise.domain.SplitType;
import com.splitwise.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class ApiDtos {

    private ApiDtos() {
    }

    public record RegisterRequest(
            @NotBlank @Size(max = 100) String name,
            @NotBlank @Pattern(regexp = "^\\+?[0-9]{7,15}$") String phoneNumber,
            @NotBlank @Size(min = 8, max = 72) String password) {
    }

    public record ProfileUpdateRequest(
            @NotBlank @Size(max = 100) String name,
            @NotBlank @Pattern(regexp = "^\\+?[0-9]{7,15}$") String phoneNumber) {
    }

    public record UserResponse(Long id, String name, String phoneNumber) {
        public static UserResponse from(User user) {
            return new UserResponse(user.getId(), user.getName(), user.getPhoneNumber());
        }
    }

    public record CreateGroupRequest(@NotBlank @Size(max = 120) String name) {
    }

    public record AddMemberRequest(@NotNull Long userId) {
    }

    public record GroupMemberResponse(Long id, String name, String phoneNumber) {
        public static GroupMemberResponse from(User user) {
            return new GroupMemberResponse(user.getId(), user.getName(), user.getPhoneNumber());
        }
    }

    public record GroupResponse(Long id, String name, Long ownerId, List<GroupMemberResponse> members) {
        public static GroupResponse from(ExpenseGroup group) {
            return new GroupResponse(
                    group.getId(),
                    group.getName(),
                    group.getOwner().getId(),
                    group.getMembers().stream().map(GroupMemberResponse::from).toList());
        }
    }

    public record PaymentInput(
            @NotNull Long userId,
            @NotNull @DecimalMin(value = "0.01") BigDecimal amount) {
    }

    public record ShareInput(
            @NotNull Long userId,
            @DecimalMin(value = "0.00", inclusive = false) BigDecimal value) {
    }

    public record CreateExpenseRequest(
            @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
            @NotBlank @Size(max = 255) String description,
            @NotNull SplitType splitType,
            Long groupId,
            @NotEmpty List<@Valid PaymentInput> payments,
            @NotEmpty List<@Valid ShareInput> shares) {
    }

    public record PaymentResponse(Long userId, String name, BigDecimal amount) {
        public static PaymentResponse from(ExpensePayment payment) {
            return new PaymentResponse(payment.getUser().getId(), payment.getUser().getName(), payment.getAmount());
        }
    }

    public record ShareResponse(Long userId, String name, BigDecimal amount) {
        public static ShareResponse from(ExpenseShare share) {
            return new ShareResponse(share.getUser().getId(), share.getUser().getName(), share.getAmount());
        }
    }

    public record ExpenseResponse(
            Long id,
            BigDecimal amount,
            String description,
            SplitType splitType,
            Long groupId,
            Long createdById,
            Instant createdAt,
            List<PaymentResponse> payments,
            List<ShareResponse> shares) {
        public static ExpenseResponse from(Expense expense) {
            return new ExpenseResponse(
                    expense.getId(),
                    expense.getAmount(),
                    expense.getDescription(),
                    expense.getSplitType(),
                    expense.getGroup() == null ? null : expense.getGroup().getId(),
                    expense.getCreatedBy().getId(),
                    expense.getCreatedAt(),
                    expense.getPayments().stream().map(PaymentResponse::from).toList(),
                    expense.getShares().stream().map(ShareResponse::from).toList());
        }
    }

    public record BalanceResponse(Long userId, String name, BigDecimal netBalance) {
    }

    public record MyBalanceResponse(
            BigDecimal netBalance,
            BigDecimal youOwe,
            BigDecimal youAreOwed) {
    }

    public record TransferResponse(Long fromUserId, String fromName, Long toUserId, String toName, BigDecimal amount) {
        public static TransferResponse from(Settlement settlement) {
            return new TransferResponse(
                    settlement.getFromUser().getId(),
                    settlement.getFromUser().getName(),
                    settlement.getToUser().getId(),
                    settlement.getToUser().getName(),
                    settlement.getAmount());
        }
    }

    public record SettlementPlanResponse(Long groupId, List<TransferResponse> transactions) {
    }

    public record SettlementExecutionResponse(
            Long groupId,
            List<TransferResponse> executedTransactions,
            String message) {
    }
}
