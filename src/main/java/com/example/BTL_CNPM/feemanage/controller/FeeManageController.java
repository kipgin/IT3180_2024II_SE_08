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

    @GetMapping("/check-one/{id}")
    public boolean existsFeeManageById(@PathVariable Integer id){
        return feeManageService.existsFeeManageById(id);
    }

    @GetMapping("/get-all/{id}")
    public List<FeeManage> findFeeManageAll(@PathVariable Integer id){
        return feeManageService.findFeeManageAll();
    }

    @GetMapping("/get-one/{id}")
    public Optional<FeeManage> getFeeManageById(@PathVariable  Integer id){
        return feeManageService.getFeeManageById(id);
    }

    @PutMapping("/update/{id}")
    public boolean updateFeeManage(@PathVariable Integer id,@RequestBody FeeManage feeManage){
        return feeManageService.updateFeeManage(id,feeManage);
    }

    @PutMapping("/payfee/{id}")
    public boolean paidFeeNormal(@PathVariable Integer id, Long fee){
        return feeManageService.paidFeeNormal(id,fee);
    }

    @PutMapping("/online-banking/{id}")
    public boolean paidFeeOnline(@PathVariable  Integer id,Long fee){
        return feeManageService.paidFeeOnline(id,fee);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteFeeManage(@PathVariable Integer id){
        return feeManageService.deleteFeeManage(id);
    }

    @PostMapping("/create")
    public boolean createFeeManage(@RequestBody FeeManage feeManage){
        return feeManageService.createFeeManage(feeManage);
    }

}
