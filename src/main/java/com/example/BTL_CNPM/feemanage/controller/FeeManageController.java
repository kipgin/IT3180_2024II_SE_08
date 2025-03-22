package com.example.BTL_CNPM.feemanage.controller;


import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.service.FeeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feemanage")
public class FeeManageController {

    @Autowired
    private FeeManageService feeManageService;

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return feeManageService.existsById(id);
    }

    @GetMapping("/check-ownerusername/{ownerUserName}")
    public boolean existsByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return feeManageService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-all")
    public List<FeeManage> findAll(){
        return feeManageService.findAll();
    }

    @GetMapping("/get-one-id/{id}")
    public FeeManage getById(@PathVariable  Integer id){
        return feeManageService.getById(id);
    }

    @GetMapping("/get-one-ownerusername/{ownerUserName}")
    public FeeManage getByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return feeManageService.getByOwnerUserName(ownerUserName);
    }

    @PutMapping("/update-id/{id}")
    public boolean updateById(@PathVariable Integer id,@RequestBody FeeManage feeManage){
        return feeManageService.updateById(id,feeManage);
    }

    @PutMapping("/update-ownerusername/{ownerUserName}")
    public boolean updateByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName ,@RequestBody FeeManage feeManage){
        return feeManageService.updateByOwnerUserName(ownerUserName,feeManage);
    }

    @PutMapping("/payfeenormal-id/{id}/{fee}")
    public boolean paidFeeNormalById(@PathVariable Integer id,@PathVariable int fee){
        return feeManageService.paidFeeNormalById(id,fee);
    }

    @PutMapping("/payfeenormal-ownerusername/{ownerUserName}/{fee}")
    public boolean paidFeeNormalById(@PathVariable("ownerUserName") String ownerUserName,@PathVariable int fee){
        return feeManageService.paidFeeNormalByOwnerUserName(ownerUserName,fee);
    }

    @PutMapping("/online-banking-id/{id}/{fee}")
    public boolean paidFeeOnlinebyId(@PathVariable  Integer id,@PathVariable int fee){
        return feeManageService.paidFeeOnlineById(id,fee);
    }

    @PutMapping("/online-banking-name/{ownerUserName}/{fee}")
    public boolean paidFeeOnlineByOwnerUserName(@PathVariable("ownerUserName")  String ownerUserName,@PathVariable int fee){
        return feeManageService.paidFeeOnlineByOwnerUserName(ownerUserName,fee);
    }
    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return feeManageService.deleteById(id);
    }

    @DeleteMapping("/delete-name/{ownerUserName}")
    public boolean deleteByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return feeManageService.deleteByOwnerUserName(ownerUserName);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody FeeManage feeManage){
        return feeManageService.add(feeManage);
    }
//    @PostMapping("/addtemp")
//    public FeeManage addtemp(@RequestBody FeeManage feeManage){
//        return feeManageService.addtemp(feeManage);
//    }

}
