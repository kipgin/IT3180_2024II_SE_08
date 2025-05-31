package com.example.BTL_CNPM.schedulefee;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleFeeService {
    @Autowired
    private ScheduleFeeRepository scheduleFeeRepository;

    public ScheduleFee findByName(String name){
        return scheduleFeeRepository.findByName(name).orElse(null);
    }
    public List<ScheduleFee> findAll(){
        return scheduleFeeRepository.findAll();
    }

    @Transactional
    public boolean add(ScheduleFee scheduleFee){
        if(scheduleFeeRepository.findByName(scheduleFee.getName()).orElse(null) != null){
            return false;
        }
        scheduleFeeRepository.save(scheduleFee);
        return true;
    }

    @Transactional
    public boolean update(ScheduleFee scheduleFee){
        if(scheduleFee == null || findByName(scheduleFee.getName()) == null){
            return false;
        }
        ScheduleFee scheduleFee1 = findByName(scheduleFee.getName());
        scheduleFee1.setDay(scheduleFee.getDay());
        scheduleFeeRepository.save(scheduleFee1);
        return true;
    }

    @Transactional
    public boolean deleteByName(ScheduleFee scheduleFee){
        if(findByName(scheduleFee.getName()) == null){
            return false;
        }
        scheduleFeeRepository.deleteByName(scheduleFee.getName());
        return true;
    }
}
