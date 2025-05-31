package com.example.BTL_CNPM.duetimefee.controller;

import com.example.BTL_CNPM.duetimefee.model.DueTimeFee;
import com.example.BTL_CNPM.duetimefee.service.DueTimeFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/duetimefee")
public class DueTimeFeeController {
    @Autowired
    private DueTimeFeeService dueTimeFeeService;

    @GetMapping("/find-by-name")
    public DueTimeFee findByName(@RequestBody DueTimeFee dueTimeFee){
        if(dueTimeFee==null){
            return null;
        }
        return dueTimeFeeService.findByName(dueTimeFee.getName());
    }

    @GetMapping("/find-all")
    public List<DueTimeFee> findAll(){
        return dueTimeFeeService.findAll();
    }

    @PutMapping("/update")
    public boolean update(@RequestBody DueTimeFee dueTimeFee){
        if(dueTimeFee==null){
            return false;
        }
        return dueTimeFeeService.update(dueTimeFee);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody DueTimeFee dueTimeFee){
        if(dueTimeFee==null){
            return false;
        }
        return dueTimeFeeService.add(dueTimeFee);
    }

    @DeleteMapping("/delete-by-name")
    public boolean deleteByName(@RequestBody DueTimeFee dueTimeFee){
        if(dueTimeFee == null){
            return false;
        }
        return dueTimeFeeService.deleteByName(dueTimeFee.getName());
    }
}
