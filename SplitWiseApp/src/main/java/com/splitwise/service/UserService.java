package com.splitwise.service;

import com.splitwise.api.ApiDtos.ProfileUpdateRequest;
import com.splitwise.api.ApiDtos.RegisterRequest;
import com.splitwise.api.ApiDtos.UserResponse;
import com.splitwise.domain.User;
import com.splitwise.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class UserService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;

    public UserService(UserRepository users, PasswordEncoder passwordEncoder, CurrentUserService currentUserService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (users.existsByPhoneNumber(request.phoneNumber())) {
            throw new ResponseStatusException(CONFLICT, "Phone number is already registered");
        }
        User user = users.save(new User(
                request.name(),
                request.phoneNumber(),
                passwordEncoder.encode(request.password())));
        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserResponse me(org.springframework.security.core.Authentication authentication) {
        return UserResponse.from(currentUserService.get(authentication));
    }

    @Transactional
    public UserResponse updateProfile(org.springframework.security.core.Authentication authentication,
                                      ProfileUpdateRequest request) {
        User user = currentUserService.get(authentication);
        users.findByPhoneNumber(request.phoneNumber())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(CONFLICT, "Phone number is already registered");
                });
        user.updateProfile(request.name(), request.phoneNumber());
        return UserResponse.from(user);
    }
}
