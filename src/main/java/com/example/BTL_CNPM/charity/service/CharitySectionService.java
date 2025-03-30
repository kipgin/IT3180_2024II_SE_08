package com.example.BTL_CNPM.charity.service;

import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharityName;
import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.repository.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CharitySectionService {
    @Autowired
    private CharitySectionRepository charitySectionRepository;

    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private CharityNameRepository charityNameRepository;

    void setCharitySectionRepository(CharitySectionRepository charitySectionRepository){
        this.charitySectionRepository=charitySectionRepository;
    }

    void setCharityRepository(CharityRepository charityRepository){
        this.charityRepository=charityRepository;
    }

    void setCharityNameRepository(CharityNameRepository charityNameRepository){
        this.charityNameRepository=charityNameRepository;
    }
    //get
    public boolean existsById(Integer id){
        return charitySectionRepository.existsById(id);
    }

    public boolean existsByName(String name){
        return charitySectionRepository.existsByName(name);
    }

    public CharitySection findById(Integer id){
        return charitySectionRepository.findById(id).orElse(null);
    }

    public List<CharitySection> findByName(String name){
        return charitySectionRepository.findByName(name);
    }

    public List<CharitySection> findAll(){
        return charitySectionRepository.findAll();
    }

    //tim theo charity

    public List<CharitySection> findByCharityId(Integer id){
        Charity charity = charityRepository.findById(id).orElse(null);
        if(charity == null){
            return null;
        }
        return charity.getCharitySections();
    }

    public List<CharitySection> findByCharityOwnerUserName(String ownerUserName){
        Charity charity =charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity==null){
            return null;
        }
        return charity.getCharitySections();
    }

    public CharitySection findOneOfOwnerByName(String name,String ownerUserName){
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return null;
        }
        List<CharitySection> tempCharitySection = charity.getCharitySections();
        for(CharitySection section : tempCharitySection){
            if(section.getName().equals(name)){
                return section;
            }
        }
        return null;
    }

    //update
    public boolean update(String ownerUserName , CharitySection charitySection){
        if(ownerUserName == null || charitySection == null || charitySection.getName() == null){
            return false;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null || charity.getOwnerUserName()== null){
            return false;
        }
        if(!charityNameRepository.existsByName(charitySection.getName()) || charitySection.getDonate() <= 0){
            return false;
        }
        List<CharitySection> tempCharitySection = charity.getCharitySections();
        for(CharitySection section : tempCharitySection){
            if(section.getName().equals(charitySection.getName())){
                section.setDonate(section.getDonate()+charitySection.getDonate());
                section.setCharity(charity);
                //charity.setCharitySections(tempCharitySection);
                charitySectionRepository.save(section);
                return true;
            }
        }

        return false;
    }

    //create
    public boolean create(String ownerUserName, CharitySection charitySection){
        if(ownerUserName == null || charitySection == null || charitySection.getName() == null){
            return false;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return false;
        }
        if(!charityNameRepository.existsByName(charitySection.getName()) || charitySection.getDonate() <= 0){
            return false;
        }
        List<CharitySection> tempCharitySection = charity.getCharitySections();
        if(!tempCharitySection.isEmpty()) {
            for (CharitySection section : tempCharitySection) {
                if (section.getName().equals(charitySection.getName())) {
                    return false;
                }
            }
        }
        charitySection.setCharity(charity);
        charity.add(charitySection);
        charityRepository.save(charity);
        return true;
    }

    //delete
    public boolean delete(String ownerUserName,CharitySection charitySection){
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null || !charityNameRepository.existsByName(charitySection.getName())){
            return false;
        }
        charity.getCharitySections().removeIf(section -> section.getName().equals(charitySection.getName()));
        charityRepository.save(charity);
        return true;
    }

    public void deleteAll(){
        charitySectionRepository.deleteAll();
    }

}
