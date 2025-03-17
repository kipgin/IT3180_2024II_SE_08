package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.FeePaid;
import com.example.BTL_CNPM.repository.FeeManageRepository;
import com.example.BTL_CNPM.repository.FeePaidRepository;
import com.example.BTL_CNPM.service.FeePaidService;
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

    //get

    @GetMapping("/check-id-available/{id}")
    public boolean existsById(@PathVariable Integer id){
        return feePaidService.existsById(id);
    }

    @GetMapping("/get-one-by-id/{id}")
    public Optional<FeePaid> findById(@PathVariable Integer id){
        return feePaidService.findById(id);
    }

    @GetMapping("/get-all")
    public List<FeePaid> findAll(){
        return feePaidService.findALl();
    }

    //put

    @PutMapping("/update/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody FeePaid feePaid){
        if(!feePaidService.existsById(id)){
            return false;
        }
        Optional<FeePaid> optionalFeePaid=feePaidService.findById(id);
        FeePaid newFeePaid=optionalFeePaid.get();
        newFeePaid=feePaid;
        feePaidRepository.save(newFeePaid);
        return true;
    }

    //post
    @PostMapping("/add/{id}")
    public boolean addById(@PathVariable Integer id,@RequestBody FeePaid feePaid){
        return feePaidService.addById(id,feePaid);
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return feePaidService.deleteById(id);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feePaidService.deleteAll();
    }
}
