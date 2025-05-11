package com.example.BTL_CNPM.feemanage.service;

import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.model.FeeName;
import com.example.BTL_CNPM.feemanage.repository.FeeNameRepository;
import jakarta.transaction.Transactional;
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

    public boolean existsByName(FeeName feeName){
        if(feeName == null || feeName.getName() == null || feeName.getName().isEmpty()){
            return false;
        }
        return feeNameRepository.existsByName(feeName.getName());
    }

    public FeeName findById(Integer id){
        return feeNameRepository.findById(id).orElse(null);
    }

    public FeeName findByName(FeeName feeName){
        if(feeName == null || feeName.getName() == null || feeName.getName().isEmpty()){
            return null;
        }
        return  feeNameRepository.findByName(feeName.getName()).orElse(null);
    }

    public List<FeeName> findAll(){
        return feeNameRepository.findAll();
    }

    @Transactional
    public boolean update(FeeName feeName, Double moneyPerBlock){
        if(feeName == null || feeName.getName() == null || feeName.getName().isEmpty() || !feeNameRepository.existsByName(feeName.getName()) || feeName.getBlock() ==null || feeName.getBlock().isEmpty()){
            return false;
        }
        String name=feeName.getName();
        FeeName feeName1 = feeNameRepository.findByName(name).orElse(null);
        feeName1.setBlock(feeName.getBlock());
        feeName1.setMoneyPerBlock(moneyPerBlock);
        feeNameRepository.save(feeName1);
        return true;
    }

    public boolean create(FeeName feeName){
        if(feeNameRepository.existsByName(feeName.getName())){
            return false;
        }
        feeNameRepository.save(feeName);
        return true;
    }

    @Transactional
    public boolean deleteById(Integer id){
        if(!feeNameRepository.existsById(id)){
            return false;
        }
        feeNameRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean deleteByName(FeeName feeName){
        if(feeName == null || feeName.getName() == null || feeName.getName().isEmpty()){
            return false;
        }
        String name=feeName.getName();
        if(!feeNameRepository.existsByName(name)){
            return false;
        }
//        System.out.println("ham delete by name");
        feeNameRepository.deleteByName(name);
        return true;
    }

    @Transactional
    public void deleteAll(){
        feeNameRepository.deleteAll();
    }
}
