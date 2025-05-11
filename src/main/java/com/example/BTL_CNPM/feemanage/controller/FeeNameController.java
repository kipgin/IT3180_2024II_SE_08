package com.example.BTL_CNPM.feemanage.controller;


import com.example.BTL_CNPM.charity.model.CharityName;
import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.model.FeeName;
import com.example.BTL_CNPM.feemanage.service.FeeNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feename")
public class FeeNameController {

    @Autowired
    private FeeNameService feeNameService;

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable  Integer id){
        return feeNameService.existsById(id);
    }

    @GetMapping("/check-name")
    public boolean existsByName(@RequestBody FeeName feeName){
        return feeNameService.existsByName(feeName);
    }

    @GetMapping("/get-id/{id}")
    public FeeName findById(@PathVariable Integer id){
        return feeNameService.findById(id);
    }

    @GetMapping("/get-name")
    public FeeName findByName(@RequestBody FeeName feeName){
        return feeNameService.findByName(feeName);
    }

    @GetMapping("/get-all")
    public List<FeeName> findAll(){
        return feeNameService.findAll();
    }

    //put
    @PutMapping("/update/{moneyperblock}")
    public boolean update(@RequestBody FeeName feeName,@PathVariable("moneyperblock") Double moneyPerBlock){
        return feeNameService.update(feeName,moneyPerBlock);
    }
    //create
    @PostMapping("/create")
    public boolean create(@RequestBody FeeName feeName){
        return feeNameService.create(feeName);
    }

    //delete
    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return feeNameService.deleteById(id);
    }

    //khong hieu sao cho nay khong dung duoc @PathVariable cho name

    @DeleteMapping("/delete-name")
    public boolean deleteByName(@RequestBody FeeName feeName){
        return feeNameService.deleteByName(feeName);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feeNameService.deleteAll();
    }

}
