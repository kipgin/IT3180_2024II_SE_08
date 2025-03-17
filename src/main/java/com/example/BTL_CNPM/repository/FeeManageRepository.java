package com.example.BTL_CNPM.repository;
import com.example.BTL_CNPM.model.FeeManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeManageRepository extends JpaRepository<FeeManage,Integer> {
    boolean existsById(Integer id);
    void deleteById(Integer id);
    Optional<FeeManage> findById (Integer id);
    List<FeeManage> findAll();

}
