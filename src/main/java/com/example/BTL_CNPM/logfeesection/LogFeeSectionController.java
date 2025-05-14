package com.example.BTL_CNPM.logfeesection;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logfeesection")
public class LogFeeSectionController {
    @Autowired
    private LogFeeSectionService logFeeSectionService;

    @GetMapping("/find-by-id/{id}")
    public LogFeeSection findById(@PathVariable("id") Integer id){
        return logFeeSectionService.findById(id);
    }

    @GetMapping("/find-all")
    public List<LogFeeSection> findAll(){
        return logFeeSectionService.findAll();
    }

    @DeleteMapping("/delete-by-id/{id}")
    public boolean deleteById(@PathVariable("id") Integer id){
        return logFeeSectionService.deleteById(id);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        logFeeSectionService.deleteAll();
    }
}
