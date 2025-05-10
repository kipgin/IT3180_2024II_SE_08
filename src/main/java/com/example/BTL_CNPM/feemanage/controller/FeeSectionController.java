package com.example.BTL_CNPM.feemanage.controller;


import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.service.FeeSectionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@RestController("/feesection")
public class FeeSectionController {
    @Autowired
    private FeeSectionService feeSectionService;

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable("id") Integer id){
        return feeSectionService.existsById(id);
    }

    @GetMapping("/check-name-of-feemanage/{ownerusername}/{name}")
    public boolean existsByNameOfFeeManange(@PathVariable("ownerusername") String ownerUserName,@PathVariable("name") String name){
        return feeSectionService.existsByNameOfFeeManange(ownerUserName,name);
    }

    @GetMapping("/find-by-id/{id}")
    public FeeSection findById(@PathVariable("id") Integer id){
        return feeSectionService.findById(id);
    }

    @GetMapping("/find-by-name-feemanage/{ownerusername}/{name}")
    public FeeSection findByNameOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@PathVariable("name") String name){
        return feeSectionService.findByNameOfFeeManage(ownerUserName,name);
    }

    @GetMapping("/find-all")
    public List<FeeSection> findAll(){
        return feeSectionService.findAll();
    }

    @PutMapping("/normal-update/{id}")
    public boolean normalUpdate(@PathVariable("id")Integer id, @RequestBody FeeSection feeSection){
        return feeSectionService.normalUpdate(id,feeSection);
    }

    @PostMapping("/normalCreate")
    public boolean normalCreate(@RequestBody FeeSection feeSection){
        return feeSectionService.normalCreate(feeSection);
    }

    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable("id") Integer id){
        return feeSectionService.deleteById(id);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feeSectionService.deleteAll();
    }
}
