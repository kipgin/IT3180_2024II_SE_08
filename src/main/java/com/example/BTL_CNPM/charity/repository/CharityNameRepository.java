package com.example.BTL_CNPM.charity.repository;

import com.example.BTL_CNPM.charity.model.CharityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CharityNameRepository extends JpaRepository<CharityName,Integer> {
    boolean existsById(Integer id);
    boolean existsByName(String name);
    Optional<CharityName> findById(Integer id);
    Optional<CharityName> findByName(String name);
    List<CharityName> findAll();
    void deleteById(Integer id);
    void deleteByName(String name);
    void deleteAll();
}
