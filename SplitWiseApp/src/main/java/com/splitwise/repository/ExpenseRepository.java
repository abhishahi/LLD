package com.splitwise.repository;

import com.splitwise.domain.Expense;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @EntityGraph(attributePaths = {"payments.user", "shares.user"})
    @Query("select e from Expense e")
    List<Expense> findAllWithParticipants();

    @EntityGraph(attributePaths = {"createdBy", "group", "payments.user", "shares.user"})
    @Query("select distinct e from Expense e left join e.payments p left join e.shares s "
            + "where p.user.id = :userId or s.user.id = :userId order by e.createdAt desc")
    List<Expense> findInvolvedByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"createdBy", "group", "payments.user", "shares.user"})
    @Query("select distinct e from Expense e where e.group.id = :groupId order by e.createdAt desc")
    List<Expense> findByGroupId(@Param("groupId") Long groupId);

    @EntityGraph(attributePaths = {"payments.user", "shares.user"})
    List<Expense> findByGroupIsNull();

    @EntityGraph(attributePaths = {"payments.user", "shares.user"})
    List<Expense> findByGroup_Id(Long groupId);
}
