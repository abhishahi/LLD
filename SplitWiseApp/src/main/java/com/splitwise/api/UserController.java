package com.splitwise.api;

import com.splitwise.api.ApiDtos.ProfileUpdateRequest;
import com.splitwise.api.ApiDtos.RegisterRequest;
import com.splitwise.api.ApiDtos.UserResponse;
import com.splitwise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService users;

    public UserController(UserService users) {
        this.users = users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return users.register(request);
    }

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        return users.me(authentication);
    }

    @PatchMapping("/me")
    public UserResponse updateProfile(Authentication authentication,
                                      @Valid @RequestBody ProfileUpdateRequest request) {
        return users.updateProfile(authentication, request);
    }
}
