package com.splitwise.repository;

import com.splitwise.domain.ExpenseGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, Long> {
    @EntityGraph(attributePaths = {"owner", "members"})
    List<ExpenseGroup> findByMembers_IdOrderByIdAsc(Long userId);

    @EntityGraph(attributePaths = {"owner", "members"})
    Optional<ExpenseGroup> findWithMembersById(Long id);
}
