package com.example.BTL_CNPM.charity.service;


import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.repository.CharityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharityService {
    @Autowired
    private CharityRepository charityRepository;

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
        return charityRepository.findById(id).orElse(null).getCharitySections();
    }

    public List<CharitySection> getAllSectionOwnerUserName(String ownerUserName){
        return charityRepository.findByOwnerUserName(ownerUserName).orElse(null).getCharitySections();
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

    public boolean update(Charity charity){
        if(!charityRepository.existsByOwnerUserName(charity.getOwnerUserName())){
            return false;
        }
        Charity existOne = charityRepository.findByOwnerUserName(charity.getOwnerUserName()).orElse(null);
        existOne.setCharitySections(charity.getCharitySections());
        existOne.setAccomStatus(charity.getAccomStatus());
        charityRepository.save(existOne);
        return true;
    }

    public boolean add(Charity charity){
        if(charityRepository.existsByOwnerUserName(charity.getOwnerUserName())){
            return false;
        }
        charityRepository.save(charity);
        return true;
    }

    public Charity addtemp(Charity charity){
        charityRepository.save(charity);
        return charity;
    }

}
