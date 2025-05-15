package com.example.BTL_CNPM.logfeetable.controller;

import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import com.example.BTL_CNPM.logfeetable.service.LogFeeTableService;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logfeetable")
public class LogFeeTableController {
    @Autowired
    private LogFeeTableService logFeeTableService;

    @GetMapping("/find-by-id/{id}")
    public LogFeeTable findById(@PathVariable("id") Integer id){
        return logFeeTableService.findById(id);
    }

    @GetMapping("/find-by-ownerusername/{ownerusername}")
    public LogFeeTable findByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return logFeeTableService.findByOwnerUserName(ownerUserName);
    }

    @GetMapping("/find-all")
    public List<LogFeeTable> findAll(){
        return logFeeTableService.findAll();
    }

    @PostMapping("/add-section-of-table/{ownerusername}")
    public boolean createSectionOfTable(@PathVariable("ownerusername") String ownerUserName,@RequestBody LogFeeSection logFeeSection){
        return logFeeTableService.createSectionOfTable(ownerUserName,logFeeSection);
    }

    @PostMapping("/add-table")
    public boolean create(@RequestBody LogFeeTable logFeeTable){
        return logFeeTableService.create(logFeeTable);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public boolean deleteById(@PathVariable("id") Integer id){
        return logFeeTableService.deleteById(id);
    }

    @DeleteMapping("/delete-by-ownerusername/{ownerusername}")
    public boolean deleteByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return logFeeTableService.deleteByOwnerUserName(ownerUserName);
    }

    @PostMapping("/delete-section-of-table/{ownerusername}/{id}")
    public boolean deleteSectionOfTable(@PathVariable("ownerusername") String ownerUserName,@PathVariable("id") Integer id){
        return logFeeTableService.deleteSectionOfTable(ownerUserName,id);
    }

    @DeleteMapping("delete-all")
    public void deleteAll(){
        logFeeTableService.deleteAll();
    }
}
