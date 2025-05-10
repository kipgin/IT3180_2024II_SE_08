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

    @GetMapping("/check-name/{name}")
    public boolean existsByName(@PathVariable("name") String name){
        return feeNameService.existsByName(name);
    }

    @GetMapping("/get-id/{id}")
    public FeeName findById(@PathVariable Integer id){
        return feeNameService.findById(id);
    }

    @GetMapping("/get-name/{name}")
    public FeeName findByName(@PathVariable("name") String name){
        return feeNameService.findByName(name);
    }

    @GetMapping("/get-all")
    public List<FeeName> findAll(){
        return feeNameService.findAll();
    }

    //put
    @PutMapping("/update/{name}/{detail}/{moneyperblock}")
    public boolean update(@PathVariable("name") String name,@PathVariable("detail") String detail,@PathVariable("moneyperblock") Double moneyPerBlock){
        return feeNameService.update(name,detail,moneyPerBlock);
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

    @DeleteMapping("/delete-name/{name}")
    public boolean deleteByName(@PathVariable("name") String name){
        return feeNameService.deleteByName(name);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feeNameService.deleteAll();
    }

}
