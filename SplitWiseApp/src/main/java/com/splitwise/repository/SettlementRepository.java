package com.splitwise.repository;

import com.splitwise.domain.Settlement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    @EntityGraph(attributePaths = {"fromUser", "toUser"})
    @Query("select s from Settlement s")
    List<Settlement> findAllWithUsers();

    @EntityGraph(attributePaths = {"fromUser", "toUser"})
    List<Settlement> findByGroupIsNull();

    @EntityGraph(attributePaths = {"fromUser", "toUser"})
    List<Settlement> findByGroup_Id(Long groupId);

    List<Settlement> findAllByGroupIsNullOrGroup_Id(Long groupId);
}
