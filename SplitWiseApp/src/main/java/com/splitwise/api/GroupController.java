package com.splitwise.api;

import com.splitwise.api.ApiDtos.AddMemberRequest;
import com.splitwise.api.ApiDtos.CreateGroupRequest;
import com.splitwise.api.ApiDtos.GroupResponse;
import com.splitwise.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groups;

    public GroupController(GroupService groups) {
        this.groups = groups;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse create(Authentication authentication, @Valid @RequestBody CreateGroupRequest request) {
        return groups.create(authentication, request);
    }

    @GetMapping
    public List<GroupResponse> list(Authentication authentication) {
        return groups.list(authentication);
    }

    @GetMapping("/{groupId}")
    public GroupResponse get(Authentication authentication, @PathVariable Long groupId) {
        return groups.get(authentication, groupId);
    }

    @PostMapping("/{groupId}/members")
    public GroupResponse addMember(Authentication authentication,
                                   @PathVariable Long groupId,
                                   @Valid @RequestBody AddMemberRequest request) {
        return groups.addMember(authentication, groupId, request.userId());
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(Authentication authentication,
                             @PathVariable Long groupId,
                             @PathVariable Long userId) {
        groups.removeMember(authentication, groupId, userId);
    }
}
