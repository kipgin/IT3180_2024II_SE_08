package com.example.BTL_CNPM.feepaid.controller;

import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feepaid.model.FeePaid;
import com.example.BTL_CNPM.feepaid.repository.FeePaidRepository;
import com.example.BTL_CNPM.feepaid.service.FeePaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//tam thoi xong, con phan thanh toan QR, chuyen khoan
@RestController
@RequestMapping("/feepaid")
public class FeePaidController {
    @Autowired
    private FeePaidService feePaidService;

    @Autowired
    private FeePaidRepository feePaidRepository;

    public void setFeePaidService(FeePaidService feePaidService){
        this.feePaidService=feePaidService;
    }

    public void setFeePaidRepository(FeePaidService feePaidService){
        this.feePaidService=feePaidService;
    }
    //get

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return feePaidService.existsById(id);
    }

    @GetMapping("/check-ownerusername/{ownerUserName}")
    public boolean existsByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return feePaidService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-all")
    public List<FeePaid> findAll(){
        return feePaidService.findALl();
    }

    @GetMapping("/get-id/{id}")
    public FeePaid findById (@PathVariable Integer id){
        return feePaidService.findById(id);
    }

    @GetMapping("/get-username/{ownerUserName}")
    public FeePaid findByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return feePaidService.findByOwnerUserName(ownerUserName);
    }

    //put

    @PutMapping("/update-id/{id}")
    public boolean updateById(@PathVariable Integer id, @RequestBody FeePaid feePaid){
        if(!feePaidService.existsById(id)){
            return false;
        }
        feePaid.setId(id);
        feePaidRepository.save(feePaid);
        return true;
    }

    @PutMapping("/update-ownerusername/{ownerUserName}")
    public boolean updateByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName,@RequestBody FeePaid feePaid){
        if(!feePaidService.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        feePaidRepository.save(feePaid);
        return true;
    }
    //post
    @PostMapping("/add")
//    public boolean add(@RequestBody FeePaid feePaid){
//        return feePaidService.add(feePaid);
//    }
    public boolean add(@RequestBody FeePaid feePaid){
        return feePaidService.add(feePaid);
    }

    @PostMapping("/addtemp")
    public FeePaid addtemp(@RequestBody FeePaid feePaid){
        return feePaidService.addtemp(feePaid);
    }

    //delete
    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return feePaidService.deleteById(id);
    }

    @DeleteMapping("/delete-ownerusername/{ownerUserName}")
    public boolean deleteByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return feePaidService.deleteByOwnerUserName(ownerUserName);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feePaidService.deleteAll();
    }
}
