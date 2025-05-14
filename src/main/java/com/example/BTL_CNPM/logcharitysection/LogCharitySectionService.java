package com.example.BTL_CNPM.logcharitysection;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogCharitySectionService {
    @Autowired
    private LogCharitySectionRepository logCharitySectionRepository;

    public boolean existsById(Integer id){
        return logCharitySectionRepository.existsById(id);
    }

    public LogCharitySection findById(Integer id){
        return logCharitySectionRepository.findById(id).orElse(null);
    }

    public List<LogCharitySection> findAll(){
        return logCharitySectionRepository.findAll();
    }

    @Transactional
    public boolean updateById(Integer id, LogCharitySection logCharitySection){
        LogCharitySection tempLogCharitySection= findById(id);
        if(tempLogCharitySection == null || logCharitySection == null || logCharitySection.getName() ==null){
            return false;
        }
        tempLogCharitySection.setName(logCharitySection.getName());
        logCharitySectionRepository.save(logCharitySection);
        return true;
    }

    public boolean create(LogCharitySection logCharitySection){
        if(logCharitySection == null){
            return false;
        }
        logCharitySectionRepository.save(logCharitySection);
        return true;
    }

    @Transactional
    public boolean deleteById(Integer id){
        if(!existsById(id)){
            return false;
        }
        logCharitySectionRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void deleteAll(){
        logCharitySectionRepository.deleteAll();
    }
}
