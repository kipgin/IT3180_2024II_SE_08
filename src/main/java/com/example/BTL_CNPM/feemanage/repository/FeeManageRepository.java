package com.example.BTL_CNPM.feemanage.repository;
import com.example.BTL_CNPM.feemanage.model.FeeManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeManageRepository extends JpaRepository<FeeManage,Integer> {
    boolean existsById(Integer id);
    boolean existsByOwnerUserName(String ownerUserName);
    void deleteById(Integer id);
    void deleteByOwnerUserName(String ownerUserName);
    Optional<FeeManage> findById (Integer id);
    List<FeeManage> findAll();
    Optional<FeeManage> findByOwnerUserName(String ownerUserName);

}
