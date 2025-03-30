package com.example.BTL_CNPM.charity.service;

import com.example.BTL_CNPM.charity.model.Charity;
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
    public boolean update(String newName, String name){
        if(charityNameRepository.existsByName(newName) || !charityNameRepository.existsByName(name)){
            return false;
        }
        CharityName existsOne =charityNameRepository.findByName(name).orElse(null);
        existsOne.setName(newName);
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


    // ham charityNameRepository.deleteByName(name) dang bi loi
    public boolean deleteByName(String name){
        CharityName charityName =charityNameRepository.findByName(name).orElse(null);
        if(charityName == null){
            return false;
        }
        charityNameRepository.deleteById(charityName.getId());
        return true;
    }

    public void deleteAll(){
        charityNameRepository.deleteAll();
    }

}