package com.example.BTL_CNPM.charity.service;

import com.example.BTL_CNPM.charity.model.CharityName;
import com.example.BTL_CNPM.charity.repository.CharityNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharityNameService {
    @Autowired
    private CharityNameRepository charityNameRepository;

    //get
    public boolean existsById(Integer id){
        return charityNameRepository.existsById(id);
    }

    public boolean existsByName(String name){
        return charityNameRepository.existsByName(name);
    }

    public CharityName findById(Integer id){
        return charityNameRepository.findById(id).orElse(null);
    }

    public CharityName findByName(String name){
        return charityNameRepository.findByName(name).orElse(null);
    }

    public List<CharityName> findAll(){
        return charityNameRepository.findAll();
    }

    //put
    public boolean update(CharityName charityName, String name){
        if(charityNameRepository.existsByName(charityName.getName())){
            return false;
        }
        CharityName existsOne =charityNameRepository.findByName(charityName.getName()).orElse(null);
        existsOne.setName(name);
        charityNameRepository.save(existsOne);
        return true;
    }
    //create
    public boolean add(CharityName charityName){
        if(charityNameRepository.existsByName(charityName.getName())){
            return false;
        }
        charityNameRepository.save(charityName);
        return true;
    }

    //delete
    public boolean deleteById(Integer id){
        if(charityNameRepository.existsById(id)){
            charityNameRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteByName(String name){
        if(charityNameRepository.existsByName(name)){
            charityNameRepository.deleteByName(name);
            return true;
        }
        return false;
    }

    public void deleteAll(){
        charityNameRepository.deleteAll();
    }

}