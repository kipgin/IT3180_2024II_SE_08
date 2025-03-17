package com.example.BTL_CNPM.service;


import com.example.BTL_CNPM.model.FeePaid;
import com.example.BTL_CNPM.repository.FeePaidRepository;
import com.example.BTL_CNPM.model.FeeManage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeePaidService {
    @Autowired
    private FeePaidRepository feePaidRepository;

    //kiem tra ton tai id

    public boolean existsById(Integer id){
        return feePaidRepository.existsById(id);
    }

    //xoa theo id

    @Transactional
    public boolean  deleteById(Integer id){
        if(!feePaidRepository.existsById(id)){
            return false;
        }
        feePaidRepository.deleteById(id);
        return true;
    }
    //kiem tra ton tai ownerUserName

    public boolean existsByOwnerUserName(String ownerUserName){
        return feePaidRepository.existsByOwnerUserName(ownerUserName);
    }

    //xoa theo username

    @Transactional
    public boolean deleteByOwnerUserName(String ownerUserName){
        if(!feePaidRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        feePaidRepository.deleteByOwnerUserName(ownerUserName);
        return true;
    }

    //xoa het
    @Transactional
    public void deleteAll(){
        feePaidRepository.deleteAll();
    }

    //add theo id

    @Transactional
    public boolean addById(Integer id,FeePaid feePaid){
        if(feePaidRepository.existsById(id) || feePaidRepository.existsByOwnerUserName(feePaid.getOwnerUsername())){
            return false;
        }

        Optional<FeePaid> optionalFeePaid=feePaidRepository.findById(id);
        FeePaid newFeePaid=optionalFeePaid.get();
        newFeePaid=feePaid;
        feePaidRepository.save(feePaid);
        return true;
    }

    //tim kiem theo id
    public Optional<FeePaid> findById (Integer id){
        return feePaidRepository.findById(id);
    }

    //tim kiem het
    public List<FeePaid> findALl(){
        return feePaidRepository.findAll();
    }
}
