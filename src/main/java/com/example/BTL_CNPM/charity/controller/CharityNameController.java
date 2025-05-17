package com.example.BTL_CNPM.charity.controller;

import com.example.BTL_CNPM.charity.model.CharityName;
import com.example.BTL_CNPM.charity.service.CharityNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/charityname")
public class CharityNameController {
    @Autowired
    private CharityNameService charityNameService;

    //get
    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable  Integer id){
        return charityNameService.existsById(id);
    }

    @GetMapping("/check-name")
    public boolean existsByName(@RequestBody CharityName charityName){
        return charityNameService.existsByName(charityName);
    }

    @GetMapping("/get-id/{id}")
    public CharityName findById(@PathVariable Integer id){
        return charityNameService.findById(id);
    }

    @GetMapping("/get-name")
    public CharityName findByName(@RequestBody CharityName charityName){
        return charityNameService.findByName(charityName);
    }

    @GetMapping("/get-all")
    public List<CharityName> findAll(){
        return charityNameService.findAll();
    }

    //put
    // khong nen dung, rat nguy hiem
    @PutMapping("/update")
    public boolean update(@RequestBody CharityName newCharityName,@RequestBody CharityName charityName){
        return charityNameService.update(newCharityName,charityName);
    }
    //create
    @PostMapping("/create")
    public boolean add(@RequestBody CharityName charityName){
        return charityNameService.add(charityName);
    }

    //delete
    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return charityNameService.deleteById(id);
    }

    //khong hieu sao cho nay khong dung duoc @PathVariable cho name

    @PostMapping("/delete-name")
    public boolean deleteByName(@RequestBody CharityName charityName){
        return charityNameService.deleteByName(charityName);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        charityNameService.deleteAll();
    }
}
