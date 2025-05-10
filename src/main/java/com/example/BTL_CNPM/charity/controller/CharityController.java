package com.example.BTL_CNPM.charity.controller;

import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.repository.CharityRepository;
import com.example.BTL_CNPM.charity.service.CharityService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
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

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return charityService.existsById(id);
    }

    @GetMapping("/check-ownerusername/{ownerUserName}")
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


    //truoc khi goi getAllSection Id/OwnerUserName thi can phai check xem id/ownerUserName co ton tai hay khong
    @GetMapping ("/get-all-section-id/{id}")
    public List<CharitySection> getAllSectionId(@PathVariable Integer id){
        return charityService.getAllSectionId(id);
    }

    @GetMapping("/get-all-section-ownerusername/{ownerUserName}")
    public List<CharitySection> getAllSectionOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.getAllSectionOwnerUserName(ownerUserName);
    }


    @GetMapping("/get-section-from-ownerusername/{sectionName}/{ownerUserName}")
    public CharitySection findCharitySectionFromOwnerUserName(@PathVariable("sectionName") String sectionName,@PathVariable("ownerUserName") String ownerUserName){
        return charityService.findCharitySectionFromOwnerUserName(sectionName,ownerUserName);
    }

    //update bi loi phan setCharitySections
    @PutMapping("/update/{ownerUserName}/{charityName}/{money}")
    public boolean update(@PathVariable("ownerUserName")String ownerUserName,@PathVariable("charityName")String charityName,@PathVariable("money") Integer money) throws MessagingException {
        return charityService.update(ownerUserName,charityName,money);
    }

    @PostMapping("/add-section-to-ownerusername/{ownerUserName}")
    public boolean addSectionToOwnerUserName(@RequestBody CharitySection charitySection,@PathVariable("ownerUserName") String ownerUserName){
        return charityService.addSectionToOwnerUserName(charitySection,ownerUserName);
    }

    //add-section-ownerusername debug
    @PostMapping("/add-section-ownerusername-temp/{ownerUserName}")
    public Charity addSectionToOwnerUserNameTemp(@RequestBody CharitySection charitySection,@PathVariable("ownerUserName") String ownerUserName){
        return charityService.addSectionToOwnerUserNameTemp(charitySection,ownerUserName);
    }


    @PostMapping("/add")
    public boolean add(@RequestBody Charity charity){
        return charityService.add(charity);
    }

    //add de debug
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
    @DeleteMapping("/delete-section-of-ownerusername/{ownerusername}/{name}")
    public boolean deleteSectionOfOwnerUserName(@PathVariable("ownerusername") String ownerUserName, @PathVariable("name") String name){
        return charityService.deleteSectionByOwnerUserName(ownerUserName,name);
    }
}
