package com.example.BTL_CNPM.logcharitytable;


import com.example.BTL_CNPM.logcharitysection.LogCharitySection;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/logcharitytable")
public class LogCharityTableController {
    @Autowired
    private LogCharityTableService logCharityTableService;

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable("id") Integer id){
        return logCharityTableService.existsById(id);
    }

    @GetMapping("/check-ownerusername/{ownerusername}")
    public boolean existsByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return logCharityTableService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-by-id/{id}")
    public LogCharityTable findById(@PathVariable("id") Integer id){
        return logCharityTableService.findById(id);
    }

    @GetMapping("/get-by-ownerusername/{ownerusername}")
    public LogCharityTable findByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return logCharityTableService.findByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-all")
    public List<LogCharityTable> findAll(){
        return logCharityTableService.findAll();
    }

    @GetMapping("/get-charitylog-by-table/{ownerusername}")
    public List<LogCharitySection> findByTable(@PathVariable("ownerusername") String ownerUserName){
        return logCharityTableService.findByTable(ownerUserName);
    }

    @PostMapping("/add-section-to-table/{ownerusername}")
    public boolean addSectionToTable(@PathVariable("ownerusername") String ownerUserName,@RequestBody LogCharitySection logCharitySection){
        return logCharityTableService.addSectionToTable(ownerUserName,logCharitySection);
    }

    @PostMapping("/create")
    public boolean create(@RequestBody LogCharityTable logCharityTable){
        return logCharityTableService.create(logCharityTable);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public boolean deleteById(@PathVariable("id") Integer id){
        return logCharityTableService.deleteById(id);
    }

    @DeleteMapping("/delete-by-ownerusername/{ownerusername}")
    public boolean deleteByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return logCharityTableService.deleteByOwnerUserName(ownerUserName);
    }

    @PostMapping("/delete-section-of-table/{ownerusername}/{id}")
    public boolean deleteSectionOfTable(@PathVariable("ownerusername") String ownerUserName,@PathVariable("id") Integer id){
        return logCharityTableService.deleteSectionOfTable(ownerUserName,id);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        logCharityTableService.deleteAll();
    }
}
