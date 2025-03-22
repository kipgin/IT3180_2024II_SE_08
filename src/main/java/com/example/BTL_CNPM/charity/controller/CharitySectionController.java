package com.example.BTL_CNPM.charity.controller;

import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.service.CharityNameService;
import com.example.BTL_CNPM.charity.service.CharitySectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/charitysection")
public class CharitySectionController {
    @Autowired
    private CharitySectionService charitySectionService;

    void setCharitySectionService(CharitySectionService charitySectionService){
        this.charitySectionService=charitySectionService;
    }

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return charitySectionService.existsById(id);
    }

    @GetMapping("/check-name/{name}")
    public boolean existsByName(@PathVariable("name") String name){
        return charitySectionService.existsByName(name);
    }

    @GetMapping("/get-id/{id}")
    public CharitySection findById(@PathVariable Integer id){
        return charitySectionService.findById(id);
    }

    @GetMapping("/get-name/{name}")
    public List<CharitySection> findByName(@PathVariable("name") String name){
        return charitySectionService.findByName(name);
    }

    @GetMapping("/get-all")
    public List<CharitySection> findAll(){
        return charitySectionService.findAll();
    }

    @GetMapping("/get-by-charity-id/{id}")
    public List<CharitySection> findByCharityId(@PathVariable Integer id){
        return charitySectionService.findByCharityId(id);
    }

    @GetMapping("/get-by-charity-name/{ownerUserName}")
    public List<CharitySection> findByCharityOwnerUsername(@PathVariable("ownerUserName") String ownerUserName){
        return charitySectionService.findByCharityOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-one-from-owner-by-name/{name}/{ownerUserName}")
    public CharitySection findOneOfOwnerByName(@PathVariable("name") String name, @PathVariable("ownerUserName") String ownerUserName){
        return charitySectionService.findOneOfOwnerByName(name,ownerUserName);
    }

    @PutMapping("/update/{ownerUserName}")
    public boolean update(@PathVariable("ownerUserName") String ownerUserName, @RequestBody CharitySection charitySection){
        return charitySectionService.update(ownerUserName,charitySection);
    }

    @PostMapping("/create/{ownerUserName}")
    public boolean create(@PathVariable("ownerUserName") String ownerUserName,@RequestBody CharitySection charitySection){
        return charitySectionService.create(ownerUserName,charitySection);
    }

    @DeleteMapping("/delete/{ownerUserName}")
    public boolean delete(@PathVariable("ownerUserName") String ownerUserName,@RequestBody CharitySection charitySection){
        return charitySectionService.delete(ownerUserName,charitySection);
    }
}
