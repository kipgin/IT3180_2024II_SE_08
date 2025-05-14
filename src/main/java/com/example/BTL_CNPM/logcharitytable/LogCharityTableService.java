package com.example.BTL_CNPM.logcharitytable;


import com.example.BTL_CNPM.household.repository.HouseholdRepository;
import com.example.BTL_CNPM.logcharitysection.LogCharitySection;
import com.example.BTL_CNPM.logcharitysection.LogCharitySectionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogCharityTableService {
    @Autowired
    private LogCharityTableRepository logCharityTableRepository;

    @Autowired
    private LogCharitySectionService logCharitySectionService;

    @Autowired
    private HouseholdRepository householdRepository;

    public boolean existsById(Integer id){
        return logCharityTableRepository.existsById(id);
    }

    public boolean existsByOwnerUserName(String ownerUserName){
        if(ownerUserName == null || ownerUserName.isEmpty()){
            return false;
        }
        return logCharityTableRepository.existsByOwnerUserName(ownerUserName);
    }

    public LogCharityTable findById(Integer id){
        return logCharityTableRepository.findById(id).orElse(null);
    }

    public LogCharityTable findByOwnerUserName(String ownerUserName){
        if(ownerUserName == null){
            return null;
        }
        return logCharityTableRepository.findByOwnerUserName(ownerUserName).orElse(null);
    }

    public List<LogCharityTable> findAll(){
        return logCharityTableRepository.findAll();
    }

    public List<LogCharitySection> findByTable(String ownerUserName){
        LogCharityTable logCharityTable = findByOwnerUserName(ownerUserName);
        if(logCharityTable == null){
            return new ArrayList<>();
        }
        return logCharityTable.getLogCharitySections();
    }

    public boolean addSectionToTable(String ownerUserName, LogCharitySection logCharitySection){
        if(logCharitySection ==null){
            return false;
        }
        LogCharityTable logCharityTable = findByOwnerUserName(ownerUserName);
        if(logCharityTable == null){
            return false;
        }
        logCharityTable.add(logCharitySection);
        logCharityTable.getLogCharitySections().add(logCharitySection);
        logCharityTableRepository.save(logCharityTable);
        return true;
    }

    public boolean create(LogCharityTable logCharityTable){
        if(logCharityTable == null || logCharityTable.getOwnerUserName() == null || !householdRepository.existsByOwnerUsername(logCharityTable.getOwnerUserName())){
            return false;
        }
        logCharityTableRepository.save(logCharityTable);
        return true;
    }

    // cái này rất lạ, vì   thực ra chỉ cần xóa theo đúng cái id là xong, nhưng mà vẫn bị hơi rườm rà.
    @Transactional
    public boolean deleteSectionOfTable(String ownerUserName, Integer id){
        LogCharityTable logCharityTable = findByOwnerUserName(ownerUserName);
        if(logCharityTable == null){
            return false;
        }

        LogCharitySection logCharitySection = logCharitySectionService.findById(id);
        for(LogCharitySection section : logCharityTable.getLogCharitySections()){
            if(section.getId() == id){
                section.setLogCharityTable(null);
                logCharityTable.getLogCharitySections().remove(section);
                logCharityTableRepository.save(logCharityTable);
                return true;
            }
        }

        return false;
    }

    @Transactional
    public boolean deleteById(Integer id){
        if(!existsById(id)){
            return false;
        }
        logCharityTableRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean deleteByOwnerUserName(String ownerUserName){
        if(!existsByOwnerUserName(ownerUserName)){
            return false;
        }
        logCharityTableRepository.deleteByOwnerUserName(ownerUserName);
        return true;
    }
    @Transactional
    public void deleteAll(){
        logCharityTableRepository.deleteAll();
    }


}
