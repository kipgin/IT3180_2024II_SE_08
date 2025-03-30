package com.example.BTL_CNPM.feepaid.repository;
import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feepaid.model.FeePaid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeePaidRepository extends JpaRepository<FeePaid,Integer> {
    boolean existsById(Integer id);
    boolean existsByOwnerUserName(String ownerUserName);
    void deleteById(Integer id);
    void deleteByOwnerUserName(String ownerUserName);
    Optional<FeePaid> findById (Integer id);
    List<FeePaid> findAll();
    Optional<FeePaid> findByOwnerUserName(String ownerUserName);
}
