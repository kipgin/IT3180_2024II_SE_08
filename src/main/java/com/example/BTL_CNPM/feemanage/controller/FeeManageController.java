package com.example.BTL_CNPM.feemanage.controller;


import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.service.FeeManageService;
import jakarta.mail.MessagingException;
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

    @GetMapping("/check-ownerusername/{ownerusername}")
    public boolean existsByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return feeManageService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/check-section-of-ownerusername/{ownerusername}")
    public boolean checkSectionOfOwnerUserName(@PathVariable("ownerusername") String ownerUserName ,@RequestBody FeeSection feeSection){
        return feeManageService.checkSectionOfOwnerUserName(ownerUserName,feeSection);
    }

    @GetMapping("/get-all")
    public List<FeeManage> findAll(){
        return feeManageService.findAll();
    }

    @GetMapping("/get-one-id/{id}")
    public FeeManage getById(@PathVariable  Integer id){
        return feeManageService.getById(id);
    }

    @GetMapping("/get-one-ownerusername/{ownerusername}")
    public FeeManage getByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return feeManageService.getByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-section-by-ownerusername/{ownerusername}")
    public FeeSection findSectionByOwnerUserName(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.findSectionByOwnerUserName(ownerUserName,feeSection);
    }

    @PutMapping("/update-id/{id}")
    public boolean updateById(@PathVariable Integer id,@RequestBody FeeManage feeManage){
        return feeManageService.updateById(id,feeManage);
    }

    @PutMapping("/update-ownerusername/{ownerusername}")
    public boolean updateByOwnerUserName(@PathVariable("ownerusername") String ownerUserName ,@RequestBody FeeManage feeManage){
        return feeManageService.updateByOwnerUserName(ownerUserName,feeManage);
    }

    @PutMapping("/update-section-of-ownerusername/{ownerusername}")
    public boolean updateSectionOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.updateSectionOfFeeManage(ownerUserName,feeSection);
    }

    //cap nhat hang thang
    @PutMapping("/update-fee/{ownerusername}")
    public Double updateFee(@PathVariable("ownerusername") String ownerUserName){
        return feeManageService.updateFee(ownerUserName);
    }

    @PutMapping("/payfeenormal-id/{id}/{fee}")
    public boolean paidFeeNormalById(@PathVariable Integer id,@PathVariable Double fee){
        return feeManageService.paidFeeNormalById(id,fee);
    }

    @PutMapping("/payfeenormal-ownerusername/{ownerUserName}/{fee}")
    public boolean paidFeeNormalByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName,@PathVariable Double fee) throws MessagingException {
        return feeManageService.paidFeeNormalByOwnerUserName(ownerUserName,fee);
    }

    @PutMapping("/online-banking-id/{id}/{fee}")
    public boolean paidFeeOnlinebyId(@PathVariable  Integer id,@PathVariable Double fee){
        return feeManageService.paidFeeOnlineById(id,fee);
    }

    @PutMapping("/online-banking-name/{ownerUserName}/{fee}")
    public boolean paidFeeOnlineByOwnerUserName(@PathVariable("ownerUserName")  String ownerUserName,@PathVariable Double fee){
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

    @PostMapping("/delete-section-of-feemanage/{ownerusername}")
    public boolean deleteSectionOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.deleteSectionOfFeeManage(ownerUserName,feeSection);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feeManageService.deleteAll();
    }

    @PostMapping("/add")
    public boolean add(@RequestBody FeeManage feeManage){
        return feeManageService.add(feeManage);
    }
//    @PostMapping("/addtemp")
//    public FeeManage addtemp(@RequestBody FeeManage feeManage){
//        return feeManageService.addtemp(feeManage);
//    }

    @PostMapping("/add-section-of-feemanage/{ownerusername}")
    public boolean addSectionOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.addSectionOfFeeManage(ownerUserName,feeSection);
    }

}
