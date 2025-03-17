package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.Charity;
import com.example.BTL_CNPM.repository.CharityRepository;
import com.example.BTL_CNPM.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/charity")
public class CharityController {
    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityRepository charityRepository;

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Integer id){
        return charityService.existsById(id);
    }

    @GetMapping("/exists/{ownerUserName}")
    public boolean existsByOwnerUserName(@PathVariable String ownerUserName){
        return charityService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-charity/{id}")
    public Optional<Charity> findById(@PathVariable Integer id){
        return charityService.findById(id);
    }

    @GetMapping("/get-charity/{ownerUserName}")
    public Optional<Charity> findByOwnerUserName(@PathVariable String ownerUserName){
        return charityService.findByOwnerUserName(ownerUserName);
    }

    @GetMapping("/all")
    public List<Charity> findAll(){
        return charityService.findAll();
    }

    @PutMapping("/update/{id}")
    public boolean updateById(@PathVariable Integer id,@RequestBody Charity charity){
        return charityService.updateById(id, charity);
    }

    @PutMapping("/update/{ownerUserName}")
    public boolean updateByOwnerUserName(@PathVariable String ownerUserName,@RequestBody Charity charity){
        return charityService.updateByUserName(ownerUserName,charity);
    }

    @PostMapping("/create")
    public boolean add(@RequestBody Charity charity){
        return charityService.add(charity);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return charityService.deleteById(id);
    }

    @DeleteMapping("/delete/{ownerUserName}")
    public boolean deleteByOwnerUserName(@PathVariable String ownerUserName){
        return charityService.deleteByOwnerUsername(ownerUserName);
    }

}
