package com.example.BTL_CNPM.feemanage.service;

import com.example.BTL_CNPM.resident.model.AccomStatus;
import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.repository.FeeManageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeManageService {
    @Autowired
    private FeeManageRepository feeManageRepository;

    //get,exist,find

    public boolean existsFeeManageById(Integer id){
        return feeManageRepository.existsById(id);
    }

    public List<FeeManage> findFeeManageAll(){
        return feeManageRepository.findAll();
    }

    public Optional<FeeManage> getFeeManageById(Integer id){
        return feeManageRepository.findById(id);
    }

    //put ( update)
    @Transactional
    public boolean updateFeeManage(Integer id, FeeManage feeManage){
        Optional<FeeManage> opFeeManage = feeManageRepository.findById(id);
        if(feeManageRepository.existsById(id)){
            FeeManage updatedFeeManage = feeManage;
            feeManageRepository.save(updatedFeeManage);
            return  true;
        }
        return false;
    }

    @Transactional
    public boolean paidFeeNormal(Integer id, Long fee){
        Optional<FeeManage> opFeeManage=feeManageRepository.findById(id);
        if(opFeeManage.isPresent()){
            FeeManage feeManage=opFeeManage.get();
            //phai kiem tra da roi di hay chua
            if(feeManage.getAccom_status()== AccomStatus.MOVED_OUT){
                return false;
            }
            feeManage.setTotalFee(feeManage.getTotalFee()-fee);
            feeManageRepository.save(feeManage);
            return true;
        }
        return false;
    }


    //phan nay dung cho quet QR,banking
    public boolean paidFeeOnline(Integer id,Long fee){

        return false;
    }

    //delete

    public boolean deleteFeeManage(Integer id){
        if(feeManageRepository.existsById(id)){
            feeManageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //create

    public boolean createFeeManage(FeeManage feeManage){
        if(feeManageRepository.existsById(feeManage.getId())){
            return false;
        }
        feeManageRepository.save(feeManage);

        return true;
    }

}
