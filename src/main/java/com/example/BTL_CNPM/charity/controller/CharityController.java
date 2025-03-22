package com.example.BTL_CNPM.charity.controller;

import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.repository.CharityRepository;
import com.example.BTL_CNPM.charity.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/exists-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return charityService.existsById(id);
    }

    @GetMapping("/exists-ownerusername/{ownerUserName}")
    public boolean existsByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-charity-id/{id}")
    public Charity findById(@PathVariable Integer id){
        return charityService.findById(id);
    }

    @GetMapping("/get-charity-ownerusername/{ownerUserName}")
    public Charity findByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.findByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-all")
    public List<Charity> findAll(){
        return charityService.findAll();
    }

    @GetMapping ("/get-all-section-id/{id}")
    public List<CharitySection> getAllSectionId(@PathVariable Integer id){
        return charityService.getAllSectionId(id);
    }

    @GetMapping("/get-all-section-ownerusername/{ownerUserName}")
    public List<CharitySection> getAllSectionOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.getAllSectionOwnerUserName(ownerUserName);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Charity charity){
        return charityService.update(charity);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Charity charity){
        return charityService.add(charity);
    }

    @PostMapping("/addtemp")
    public Charity addtemp(@RequestBody Charity charity){
        return charityService.addtemp(charity);
    }

    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return charityService.deleteById(id);
    }

    @DeleteMapping("/delete-ownerusername/{ownerUserName}")
    public boolean deleteByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.deleteByOwnerUsername(ownerUserName);
    }

}
