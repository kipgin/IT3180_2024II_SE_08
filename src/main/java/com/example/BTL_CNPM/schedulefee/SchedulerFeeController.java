package com.example.BTL_CNPM.schedulefee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedulefee")
public class SchedulerFeeController {

    @Autowired
    private ScheduleFeeService scheduleFeeService;
    @GetMapping("/find-by-name")
    public ScheduleFee findByName(@RequestBody ScheduleFee scheduleFee){
        if(scheduleFee == null){
            return null;
        }
        return scheduleFeeService.findByName(scheduleFee.getName());
    }

    @GetMapping("/find-all")
    public List<ScheduleFee> findAll(){
        return scheduleFeeService.findAll();
    }
    @PostMapping("/add")
    public boolean add(@RequestBody ScheduleFee scheduleFee){
        if(scheduleFee == null){
            return false;
        }
        return scheduleFeeService.add(scheduleFee);
    }

    @PutMapping("/put")
    public boolean update(@RequestBody ScheduleFee scheduleFee){
        if(scheduleFee == null){
            return false;
        }
        return scheduleFeeService.update(scheduleFee);
    }

    @DeleteMapping("/delete-by-name")
    public boolean deleteByName(@RequestBody ScheduleFee scheduleFee){
        if(scheduleFee == null){
            return false;
        }
        return scheduleFeeService.deleteByName(scheduleFee);
    }

}
