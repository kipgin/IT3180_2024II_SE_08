package com.example.BTL_CNPM.charity.service;

import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharityName;
import com.example.BTL_CNPM.charity.repository.CharityNameRepository;

import jakarta.transaction.Transactional;
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

    public boolean existsByName(CharityName charityName){
        if(charityName == null || charityName.getName()==null || charityName.getName().isEmpty()){
            return false;
        }
        return charityNameRepository.existsByName(charityName.getName());
    }

    public CharityName findById(Integer id){
        return charityNameRepository.findById(id).orElse(null);
    }

    public CharityName findByName(CharityName charityName){
        if(charityName == null || charityName.getName()==null || charityName.getName().isEmpty()){
            return null;
        }
        return charityNameRepository.findByName(charityName.getName()).orElse(null);
    }

    public List<CharityName> findAll(){
        return charityNameRepository.findAll();
    }

    //put
    //khong nen dung, tot nhat la xoa xong tao cai moi , chu khong nen update nhu nay
    @Transactional
    public boolean update(CharityName newCharityName, CharityName charityName){
        if(newCharityName==null || charityName ==null|| newCharityName.getName() == null || newCharityName.getName().isEmpty() || charityName.getName() == null || charityName.getName().isEmpty()){
            return false;
        }
        String name = newCharityName.getName();
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
    @Transactional
    public boolean deleteById(Integer id){
        if(charityNameRepository.existsById(id)){
            charityNameRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // ham charityNameRepository.deleteByName(name) dang bi loi
    @Transactional
    public boolean deleteByName(CharityName charityName){
        if(charityName == null || charityName.getName()==null || charityName.getName().isEmpty()){
            return false;
        }
        String name=charityName.getName();
        CharityName charityName1 =findByName(charityName);
        if(charityName1 == null){
            return false;
        }
        charityNameRepository.deleteByName(name);
        return true;
    }

    //ko nen dung ham nay
    @Transactional
    public void deleteAll(){
        charityNameRepository.deleteAll();
    }

}