package com.splitwise.api;

import com.splitwise.api.ApiDtos.BalanceResponse;
import com.splitwise.api.ApiDtos.MyBalanceResponse;
import com.splitwise.service.SettlementService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BalanceController {

    private final SettlementService settlements;

    public BalanceController(SettlementService settlements) {
        this.settlements = settlements;
    }

    @GetMapping("/balances/me")
    public MyBalanceResponse myBalance(Authentication authentication) {
        return settlements.myBalance(authentication);
    }

    @GetMapping("/groups/{groupId}/balances")
    public List<BalanceResponse> groupBalances(Authentication authentication, @PathVariable Long groupId) {
        return settlements.groupBalances(authentication, groupId);
    }
}
