package com.splitwise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "expense_groups")
public class ExpenseGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new LinkedHashSet<>();

    protected ExpenseGroup() {
    }

    public ExpenseGroup(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.members.add(owner);
    }

    public boolean addMember(User user) {
        return members.add(user);
    }

    public boolean removeMember(User user) {
        if (owner.getId().equals(user.getId())) {
            return false;
        }
        return members.removeIf(member -> member.getId().equals(user.getId()));
    }

    public boolean hasMember(Long userId) {
        return members.stream().anyMatch(member -> member.getId().equals(userId));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public Set<User> getMembers() {
        return members;
    }
}
