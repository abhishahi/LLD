package com.splitwise.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SplitType splitType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private ExpenseGroup group;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExpensePayment> payments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExpenseShare> shares = new LinkedHashSet<>();

    protected Expense() {
    }

    public Expense(BigDecimal amount, String description, SplitType splitType, User createdBy, ExpenseGroup group) {
        this.amount = amount;
        this.description = description;
        this.splitType = splitType;
        this.createdBy = createdBy;
        this.group = group;
        this.createdAt = Instant.now();
    }

    public void addPayment(User user, BigDecimal amount) {
        payments.add(new ExpensePayment(this, user, amount));
    }

    public void addShare(User user, BigDecimal amount) {
        shares.add(new ExpenseShare(this, user, amount));
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public ExpenseGroup getGroup() {
        return group;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<ExpensePayment> getPayments() {
        return payments;
    }

    public Set<ExpenseShare> getShares() {
        return shares;
    }
}
