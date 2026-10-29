package com.splitwise.service;

import com.splitwise.api.ApiDtos.CreateGroupRequest;
import com.splitwise.api.ApiDtos.GroupResponse;
import com.splitwise.domain.ExpenseGroup;
import com.splitwise.domain.User;
import com.splitwise.repository.ExpenseGroupRepository;
import com.splitwise.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@Service
public class GroupService {

    private final ExpenseGroupRepository groups;
    private final UserRepository users;
    private final CurrentUserService currentUserService;

    public GroupService(ExpenseGroupRepository groups, UserRepository users, CurrentUserService currentUserService) {
        this.groups = groups;
        this.users = users;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public GroupResponse create(Authentication authentication, CreateGroupRequest request) {
        User owner = currentUserService.get(authentication);
        return GroupResponse.from(groups.save(new ExpenseGroup(request.name(), owner)));
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> list(Authentication authentication) {
        User user = currentUserService.get(authentication);
        return groups.findByMembers_IdOrderByIdAsc(user.getId()).stream().map(GroupResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public GroupResponse get(Authentication authentication, Long groupId) {
        return GroupResponse.from(requireMember(authentication, groupId));
    }

    @Transactional
    public GroupResponse addMember(Authentication authentication, Long groupId, Long userId) {
        ExpenseGroup group = requireOwner(authentication, groupId);
        User member = users.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        group.addMember(member);
        return GroupResponse.from(group);
    }

    @Transactional
    public GroupResponse removeMember(Authentication authentication, Long groupId, Long userId) {
        ExpenseGroup group = requireOwner(authentication, groupId);
        if (group.getOwner().getId().equals(userId)) {
            throw new ResponseStatusException(BAD_REQUEST, "The group owner cannot be removed");
        }
        User member = users.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        if (!group.removeMember(member)) {
            throw new ResponseStatusException(NOT_FOUND, "User is not a member of this group");
        }
        return GroupResponse.from(group);
    }

    @Transactional(readOnly = true)
    public ExpenseGroup requireMember(Authentication authentication, Long groupId) {
        User actor = currentUserService.get(authentication);
        ExpenseGroup group = groups.findWithMembersById(groupId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Group not found"));
        if (!group.hasMember(actor.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You are not a member of this group");
        }
        return group;
    }

    private ExpenseGroup requireOwner(Authentication authentication, Long groupId) {
        User actor = currentUserService.get(authentication);
        ExpenseGroup group = groups.findWithMembersById(groupId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Group not found"));
        if (!group.getOwner().getId().equals(actor.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "Only the group owner can manage members");
        }
        return group;
    }
}
