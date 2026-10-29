package com.splitwise.api;

import com.splitwise.api.ApiDtos.SettlementExecutionResponse;
import com.splitwise.api.ApiDtos.SettlementPlanResponse;
import com.splitwise.service.SettlementService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settlements")
public class SettlementController {

    private final SettlementService settlements;

    public SettlementController(SettlementService settlements) {
        this.settlements = settlements;
    }

    @org.springframework.web.bind.annotation.GetMapping("/preview")
    public SettlementPlanResponse preview(Authentication authentication,
                                          @RequestParam(required = false) Long groupId) {
        return settlements.preview(authentication, groupId);
    }

    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.CREATED)
    public SettlementExecutionResponse execute(Authentication authentication,
                                               @RequestParam(required = false) Long groupId) {
        return settlements.execute(authentication, groupId);
    }
}
