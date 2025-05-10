package com.example.BTL_CNPM.feemanage.service;

import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.model.FeeName;
import com.example.BTL_CNPM.feemanage.repository.FeeNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeNameService {

    @Autowired
    private FeeNameRepository feeNameRepository;

    public boolean existsById(Integer id){
        return feeNameRepository.existsById(id);
    }

    public boolean existsByName(String name){
        return feeNameRepository.existsByName(name);
    }

    public FeeName findById(Integer id){
        return feeNameRepository.findById(id).orElse(null);
    }

    public FeeName findByName(String name){
        return  feeNameRepository.findByName(name).orElse(null);
    }

    public List<FeeName> findAll(){
        return feeNameRepository.findAll();
    }

    public boolean update(String name,String block, Double moneyPerBlock){
        if(!feeNameRepository.existsByName(name)){
            return false;
        }
        FeeName feeName = feeNameRepository.findByName(name).orElse(null);
        feeName.setBlock(block);
        feeName.setMoneyPerBlock(moneyPerBlock);
        feeNameRepository.save(feeName);
        return true;
    }

    public boolean create(FeeName feeName){
        if(feeNameRepository.existsByName(feeName.getName())){
            return false;
        }
        feeNameRepository.save(feeName);
        return true;
    }

    public boolean deleteById(Integer id){
        if(!feeNameRepository.existsById(id)){
            return false;
        }
        feeNameRepository.deleteById(id);
        return true;
    }

    public boolean deleteByName(String name){
        if(!feeNameRepository.existsByName(name)){
            return false;
        }
        feeNameRepository.deleteByName(name);
        return true;
    }

    public void deleteAll(){
        feeNameRepository.deleteAll();
    }
}
