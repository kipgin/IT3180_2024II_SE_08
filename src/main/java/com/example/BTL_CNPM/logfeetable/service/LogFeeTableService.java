package com.example.BTL_CNPM.logfeetable.service;

import com.example.BTL_CNPM.household.repository.HouseholdRepository;
import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import com.example.BTL_CNPM.logfeetable.repository.LogFeeTableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogFeeTableService {
    @Autowired
    private LogFeeTableRepository logFeeTableRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    public LogFeeTable findById(Integer id){
        return logFeeTableRepository.findById(id).orElse(null);
    }

    public LogFeeTable findByOwnerUserName(String ownerUserName){
        return logFeeTableRepository.findByOwnerUserName(ownerUserName).orElse(null);
    }

    public List<LogFeeTable> findAll(){
        return logFeeTableRepository.findAll();
    }

    public boolean create(LogFeeTable logFeeTable){
        if(logFeeTable == null || logFeeTable.getOwnerUserName().isEmpty()){
            return false;
        }
        if(householdRepository.findByOwnerUsername(logFeeTable.getOwnerUserName()).orElse(null)==null || findByOwnerUserName(logFeeTable.ownerUserName) != null){
            return false;
        }
        logFeeTableRepository.save(logFeeTable);
        return true;
    }

    public boolean createSectionOfTable(String ownerUserName, LogFeeSection logFeeSection){
        if(ownerUserName==null || ownerUserName.isEmpty() || logFeeSection == null ){
            return false;
        }
        LogFeeTable logFeeTable = findByOwnerUserName(ownerUserName);
        logFeeTable.add(logFeeSection);
        logFeeTableRepository.save(logFeeTable);
        return true;
    }

    @Transactional
    public boolean deleteSectionOfTable(String ownerUserName,Integer id){
        if(ownerUserName==null || ownerUserName.isEmpty() || id == null  || !logFeeTableRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        LogFeeTable logFeeTable = findByOwnerUserName(ownerUserName);
        for(LogFeeSection section : logFeeTable.getLogFeeSectionList()){
            if(section.getId()==id){
                section.setLogFeeTable(null);
                logFeeTable.getLogFeeSectionList().remove(section);
                logFeeTableRepository.save(logFeeTable);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean deleteByOwnerUserName(String ownerUserName){
        if(ownerUserName==null || findByOwnerUserName(ownerUserName)==null){
            return false;
        }
        logFeeTableRepository.deleteByOwnerUserName(ownerUserName);
        return true;
    }

    @Transactional
    public boolean deleteById(Integer id){
        if(findById(id) == null){
            return false;
        }
        logFeeTableRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void deleteAll(){
        logFeeTableRepository.deleteAll();
    }
}
