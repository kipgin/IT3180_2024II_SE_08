package com.example.BTL_CNPM.logcharitysection;


import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeesection.LogFeeSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logcharitysection")
public class LogCharitySectionController {

    @Autowired
    private LogCharitySectionService logCharitySectionService;

    @GetMapping("/exists-by-id/{id}")
    public boolean existsById(@PathVariable("id") Integer id){
        return logCharitySectionService.existsById(id);
    }

    @GetMapping("/get-by-id/{id}")
    public LogCharitySection findById(@PathVariable("id") Integer id){
        return logCharitySectionService.findById(id);
    }

    @GetMapping("/get-all")
    public List<LogCharitySection> findAll(){
        return logCharitySectionService.findAll();
    }

    @GetMapping("/debug")
    public void debug(){
        List<LogCharitySection> logs = logCharitySectionService.findAll();
        for(int i = 0 ; i < logs.size() ;i++){
            System.out.println(logs.get(i).getLogCharityTable());
        }
    }

    @PutMapping("/update-by-id/{id}")
    public boolean updateById(@PathVariable("id") Integer id, LogCharitySection logCharitySection){
        return logCharitySectionService.updateById(id, logCharitySection);
    }

    @PostMapping("/create")
    public boolean create(LogCharitySection logCharitySection){
        return logCharitySectionService.create(logCharitySection);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public boolean deleteById(@PathVariable("id") Integer id,LogCharitySection logCharitySection){
        return logCharitySectionService.deleteById(id);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        logCharitySectionService.deleteAll();
    }
}
