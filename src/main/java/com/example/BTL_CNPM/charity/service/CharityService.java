package com.example.BTL_CNPM.charity.service;


import com.example.BTL_CNPM.charity.model.Charity;
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

    public Optional<Charity> findById(Integer id){
        return charityRepository.findById(id);
    }

    public Optional<Charity> findByOwnerUserName(String ownerUserName){
        return charityRepository.findByOwnerUserName(ownerUserName);
    }
    public List<Charity> findAll(){
        return charityRepository.findAll();
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

    public boolean updateById(Integer id,Charity charity){
        if(!charityRepository.existsById(id)){
            return false;
        }
        Charity newCharity=charityRepository.findById(id).get();
        newCharity=charity;
        charityRepository.save(charity);
        return true;
    }

    public boolean updateByUserName(String ownerUserName,Charity charity){
        if(!charityRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        Charity newCharity =charityRepository.findByOwnerUserName(ownerUserName).get();
        charityRepository.save(newCharity);
        return true;
    }

    public boolean add(Charity charity){
        if(charityRepository.existsById(charity.getId()) || charityRepository.existsByOwnerUserName(charity.getOwnerUserName())){
            return false;
        }
        charityRepository.save(charity);
        return true;
    }

}
