package com.splitwise.api;

import com.splitwise.api.ApiDtos.CreateExpenseRequest;
import com.splitwise.api.ApiDtos.ExpenseResponse;
import com.splitwise.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseService expenses;

    public ExpenseController(ExpenseService expenses) {
        this.expenses = expenses;
    }

    @PostMapping("/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse create(Authentication authentication, @Valid @RequestBody CreateExpenseRequest request) {
        return expenses.create(authentication, request);
    }

    @GetMapping("/expenses")
    public List<ExpenseResponse> history(Authentication authentication) {
        return expenses.history(authentication);
    }

    @GetMapping("/groups/{groupId}/expenses")
    public List<ExpenseResponse> groupHistory(Authentication authentication, @PathVariable Long groupId) {
        return expenses.groupHistory(authentication, groupId);
    }
}
