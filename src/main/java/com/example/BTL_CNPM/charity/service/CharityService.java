package com.example.BTL_CNPM.charity.service;


import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.repository.CharityNameRepository;
import com.example.BTL_CNPM.charity.repository.CharityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CharityService {
    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private CharityNameRepository charityNameRepository;

    public boolean existsById(Integer id){
        return charityRepository.existsById(id);
    }

    public boolean existsByOwnerUserName(String ownerUserName){
        return charityRepository.existsByOwnerUserName(ownerUserName);
    }

    public Charity findById(Integer id){
        return charityRepository.findById(id).orElse(null);
    }

    public Charity findByOwnerUserName(String ownerUserName){
        return charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
    }
    public List<Charity> findAll(){
        return charityRepository.findAll();
    }

    public List<CharitySection> getAllSectionId(Integer id){
        return Objects.requireNonNull(charityRepository.findById(id).orElse(null)).getCharitySections();
    }

    public List<CharitySection> getAllSectionOwnerUserName(String ownerUserName){
        return Objects.requireNonNull(charityRepository.findByOwnerUserName(ownerUserName).orElse(null)).getCharitySections();
    }
    @Transactional
    public boolean deleteById(Integer id){
        if(!charityRepository.existsById(id)){
            return false;
        }
        charityRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean deleteByOwnerUsername(String ownerUserName){
        if(!charityRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        charityRepository.deleteByOwnerUserName(ownerUserName);
        return true;
    }

    //update bi loi phan setCharitySections
    public boolean update(Charity charity){
        if(charity.getOwnerUserName() == null || !charityRepository.existsByOwnerUserName(charity.getOwnerUserName())){
            return false;
        }
        Charity existOne = charityRepository.findByOwnerUserName(charity.getOwnerUserName()).orElse(null);
        //existOne.setCharitySections(charity.getCharitySections());
        existOne.setAccomStatus(charity.getAccomStatus());
        charityRepository.save(existOne);
        return true;
    }

    public CharitySection findCharitySectionFromOwnerUserName(String sectionName,String ownerUserName){
        if(sectionName == null || ownerUserName == null){
            return null;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return null;
        }
        List<CharitySection> tempCharitySection = charity.getCharitySections();
        for(CharitySection section : tempCharitySection){
            if(section == null || section.getName() == null){
                break;
            }
            if(section.getName().equals(sectionName)){
                return section;
            }
        }
        return null;
    }

    public boolean addSectionToOwnerUserName(CharitySection charitySection, String ownerUserName){
        if(ownerUserName == null || charitySection == null || charitySection.getName()==null){
            return false;
        }
        if(!charityNameRepository.existsByName(charitySection.getName())){
            return false;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return false;
        }
        List<CharitySection> tempCharitySection=charity.getCharitySections();
        if(!tempCharitySection.isEmpty()) {
            for (CharitySection section : tempCharitySection) {
                if (section == null || section.getName() == null) {
                    break;
                }
                if (section.getName().equals(charitySection.getName())) {
                    return false;
                }
            }
        }
        charity.add(charitySection);
        charityRepository.save(charity);
        return true;
    }
    public Charity addSectionToOwnerUserNameTemp(CharitySection charitySection, String ownerUserName){
        if(ownerUserName == null || charitySection == null || charitySection.getName()==null){
            return null ;
        }
        if(!charityNameRepository.existsByName(charitySection.getName())){
            return null;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return charity;
        }
        List<CharitySection> tempCharitySection=charity.getCharitySections();
        if(!tempCharitySection.isEmpty()) {
            for (CharitySection section : tempCharitySection) {
                if (section == null || section.getName() == null) {
                    return charity;
                }
                if (section.getName().equals(charitySection.getName())) {
                    return charity;
                }
            }
        }
        charity.add(charitySection);
        if(1+1 == 2){
            return charity;
        }
        charityRepository.save(charity);
        return charity;
    }

    public boolean add(Charity charity){
        if(charity==null || charity.getOwnerUserName()==null || charityRepository.existsByOwnerUserName(charity.getOwnerUserName())){
            return false;
        }
        charity.setCharitySections(new ArrayList<>());
        charityRepository.save(charity);
        return true;
    }

    public Charity addtemp(Charity charity){
        charityRepository.save(charity);
        return charity;
    }

}
