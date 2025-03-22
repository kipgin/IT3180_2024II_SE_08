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

    @GetMapping("/check-name/{name}")
    public boolean existsByName(@PathVariable("name") String name){
        return charityNameService.existsByName(name);
    }

    @GetMapping("/get-id/{id}")
    public CharityName findById(@PathVariable Integer id){
        return charityNameService.findById(id);
    }

    @GetMapping("/get-name/{name}")
    public CharityName findByName(@PathVariable("name") String name){
        return charityNameService.findByName(name);
    }

    @GetMapping("/get-all")
    public List<CharityName> findAll(){
        return charityNameService.findAll();
    }

    //put
    @PutMapping("/update/{name}")
    public boolean update(@RequestBody CharityName charityName,@PathVariable("name") String name){
        return charityNameService.update(charityName,name);
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

    @DeleteMapping("/delete-name/{name}")
    public boolean deleteByName(@PathVariable("name") String name){
        return charityNameService.deleteByName(name);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        charityNameService.deleteAll();
    }
}
