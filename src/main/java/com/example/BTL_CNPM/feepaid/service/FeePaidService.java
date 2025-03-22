package com.example.BTL_CNPM.feepaid.service;


import com.example.BTL_CNPM.feepaid.model.FeePaid;
import com.example.BTL_CNPM.feepaid.repository.FeePaidRepository;
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
    //kiem tra ton tai ownerUserName

    public boolean existsByOwnerUserName(String ownerUserName){
        return feePaidRepository.existsByOwnerUserName(ownerUserName);
    }

    //tim kiem theo id
    public FeePaid findById (Integer id){
        return feePaidRepository.findById(id).orElse(null);
    }

    public FeePaid findByOwnerUserName(String ownerUserName){
        return feePaidRepository.findByOwnerUserName(ownerUserName).orElse(null);
    }

    //tim kiem het
    public List<FeePaid> findALl(){
        return feePaidRepository.findAll();
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

    @Transactional
    public boolean add(FeePaid feePaid){
        if(feePaidRepository.existsById(feePaid.getId()) || feePaidRepository.existsByOwnerUserName(feePaid.getOwnerUsername())){
            return false;
        }
        feePaid.setTotalFee(0);
        feePaidRepository.save(feePaid);
        return true;
    }

}
