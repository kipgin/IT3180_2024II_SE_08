package com.example.BTL_CNPM.feemanage.service;


import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.repository.FeeManageRepository;
import com.example.BTL_CNPM.feemanage.repository.FeeNameRepository;
import com.example.BTL_CNPM.feemanage.repository.FeeSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeSectionService {
    @Autowired
    private FeeSectionRepository feeSectionRepository;

    @Autowired
    private FeeManageRepository feeManageRepository;

    @Autowired
    private FeeNameRepository feeNameRepository;

    public boolean existsById(Integer id){
        return feeSectionRepository.existsById(id);
    }

    public boolean existsByNameOfFeeManange(String ownerUserName,String name){
        if(!feeManageRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        FeeManage feeManage=feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        List<FeeSection> list= feeManage.getFeeSections();
        if(list.isEmpty()){
            return false;
        }
        for(int i = 0 ; i < list.size(); i++){
            if(list.get(i).getName().equals(name)){
                return false;
            }
        }
        return false;
    }

    public FeeSection findById(Integer id){
        return feeSectionRepository.findById(id).orElse(null);
    }

    public FeeSection findByNameOfFeeManage(String ownerUserName,String name){
        if(!feeManageRepository.existsByOwnerUserName(ownerUserName)){
            return null;
        }
        FeeManage feeManage=feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        List<FeeSection> list= feeManage.getFeeSections();
        if(list.isEmpty()){
            return null;
        }
        if(!existsByNameOfFeeManange(ownerUserName,name)){
            return null;
        }
        for(int i = 0 ; i < list.size(); i++){
            if(list.get(i).getName().equals(name)){
                return list.get(i);
            }
        }
        return null;
    }

    public List<FeeSection> findByName(String name){
        return feeSectionRepository.findByName(name);
    }

    public List<FeeSection> findAll(){
        return feeSectionRepository.findAll();
    }

    public boolean normalUpdate(Integer id, FeeSection feeSection){
        if(!feeSectionRepository.existsById(id)){
            return false;
        }
        if(feeSection.getName() == null || feeSection.getName().isEmpty() || !feeNameRepository.existsByName(feeSection.getName())){
            return false;
        }
        FeeSection feeSection1 = feeSectionRepository.findById(id).orElse(null);
        feeSection1.setBlockUsed(feeSection.getBlockUsed());
        feeSectionRepository.save(feeSection1);
        return true;
    }

    public boolean normalCreate(FeeSection feeSection){
        if(feeSection.getName() == null || feeSection.getName().isEmpty() || !feeNameRepository.existsByName(feeSection.getName())){
            return false;
        }
        feeSectionRepository.save(feeSection);
        return true;
    }

    public boolean deleteById(Integer id){
        if(!feeSectionRepository.existsById(id)){
            return false;
        }
        feeSectionRepository.deleteById(id);
        return true;
    }

    public void deleteAll(){
        feeSectionRepository.deleteAll();
    }
}
