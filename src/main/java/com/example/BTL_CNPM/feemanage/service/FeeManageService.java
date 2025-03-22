package com.example.BTL_CNPM.feemanage.service;


import com.example.BTL_CNPM.resident.model.AccomStatus;
import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.repository.FeeManageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.BTL_CNPM.resident.model.AccomStatus.MOVED_OUT;

@Service
public class FeeManageService {
    @Autowired
    private FeeManageRepository feeManageRepository;

    public void setFeeManageRepository(FeeManageRepository feeManageRepository) {
        this.feeManageRepository = feeManageRepository;
    }

    //get,exist,find


    public boolean existsById(Integer id){
        return feeManageRepository.existsById(id);
    }

    public boolean existsByOwnerUserName(String ownerUserName){
        return feeManageRepository.existsByOwnerUserName(ownerUserName);
    }

    public List<FeeManage> findAll(){
        return feeManageRepository.findAll();
    }

    public FeeManage getById(Integer id){
        return feeManageRepository.findById(id).orElse(null);
    }

    public FeeManage getByOwnerUserName(String ownerUserName){
        return feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
    }

    //put ( update)
    @Transactional
    public boolean updateById(Integer id, FeeManage feeManage){
        if(!feeManageRepository.existsById(id)){
            return false;
        }
        FeeManage tempFeeManage = feeManageRepository.findById(id).get();
        tempFeeManage = feeManage;
        feeManageRepository.save(tempFeeManage);
        return true;
    }
    public boolean updateByOwnerUserName(String ownerUserName,FeeManage feeManage){
        if(!feeManageRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        FeeManage tempFeeManage=feeManageRepository.findByOwnerUserName(ownerUserName).get();
        feeManageRepository.save(tempFeeManage);
        return true;
    }
    @Transactional
    public boolean paidFeeNormalById(Integer id, int fee){
        FeeManage feeManage = feeManageRepository.findById(id).orElse(null);
        if(feeManage == null || feeManage.getAccom_status()== MOVED_OUT ){
            return false;
        }
        feeManage.setTotalFee((int) (feeManage.getTotalFee()-fee));
        feeManageRepository.save(feeManage);
        return true;
    }

    @Transactional
    public boolean paidFeeNormalByOwnerUserName(String ownerUserName, int fee){
        FeeManage feeManage = feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(feeManage==null || feeManage.getAccom_status()== MOVED_OUT ){
            return false;
        }
        feeManage.setTotalFee((int) (feeManage.getTotalFee()-fee));
        feeManageRepository.save(feeManage);
        return true;
    }


    //phan nay dung cho quet QR,banking
    public boolean paidFeeOnlineById(Integer id,int fee){

        return false;
    }

    public boolean paidFeeOnlineByOwnerUserName(String ownerUserName,int fee){
        return false;
    }

    //delete

    public boolean deleteById(Integer id){
        if(feeManageRepository.existsById(id)){
            feeManageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteByOwnerUserName(String ownerUserName){
        if(feeManageRepository.existsByOwnerUserName(ownerUserName)){
            feeManageRepository.deleteByOwnerUserName(ownerUserName);
            return true;
        }
        return false;
    }

    //create

    public boolean createFeeManage(FeeManage feeManage){
        if(feeManageRepository.existsById(feeManage.getId()) || feeManageRepository.existsByOwnerUserName(feeManage.getOwnerUserName())){
            return false;
        }
        feeManageRepository.save(feeManage);
        return true;
    }

}
