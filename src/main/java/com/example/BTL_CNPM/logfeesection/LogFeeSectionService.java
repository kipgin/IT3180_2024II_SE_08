package com.example.BTL_CNPM.logfeesection;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogFeeSectionService {
    @Autowired
    private LogFeeSectionRepository logFeeSectionRepository;

    public LogFeeSection findById(Integer id){
        return logFeeSectionRepository.findById(id).orElse(null);
    }

    public List<LogFeeSection> findAll(){
        return logFeeSectionRepository.findAll();
    }

    @Transactional
    public boolean deleteById(Integer id){
        if(findById(id) == null){
            return false;
        }
        logFeeSectionRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void deleteAll(){
        logFeeSectionRepository.deleteAll();
    }
}
