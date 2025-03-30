package com.example.BTL_CNPM.charity.repository;

import com.example.BTL_CNPM.charity.model.CharitySection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CharitySectionRepository extends JpaRepository<CharitySection,Integer> {
    boolean existsById(Integer id);
    boolean existsByName(String name);
    Optional<CharitySection> findById(Integer id);
    List<CharitySection> findByName(String name);
    List<CharitySection> findAll();
    void deleteById(Integer id);
    void deleteByName(String ownerUserName);
    void deleteAll();
}
