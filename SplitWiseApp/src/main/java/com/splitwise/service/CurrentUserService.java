package com.splitwise.service;

import com.splitwise.domain.User;
import com.splitwise.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class CurrentUserService {

    private final UserRepository users;

    public CurrentUserService(UserRepository users) {
        this.users = users;
    }

    public User get(Authentication authentication) {
        return users.findByPhoneNumber(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Authenticated user no longer exists"));
    }
}
