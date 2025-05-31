package com.example.BTL_CNPM.duetimefee.service;


import com.example.BTL_CNPM.duetimefee.model.DueTimeFee;
import com.example.BTL_CNPM.duetimefee.repository.DueTimeFeeRepository;
import com.example.BTL_CNPM.feemanage.controller.FeeManageController;
import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.repository.FeeManageRepository;
import com.example.BTL_CNPM.feemanage.service.FeeManageService;
import com.example.BTL_CNPM.gmail.model.users.UsersGmail;
import com.example.BTL_CNPM.gmail.service.UsersGmailService;
import com.example.BTL_CNPM.schedulefee.ScheduleFee;
import com.example.BTL_CNPM.schedulefee.ScheduleFeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DueTimeFeeService {
    @Autowired
    private DueTimeFeeRepository dueTimeFeeRepository;

    @Autowired
    private FeeManageRepository feeManageRepository;

    @Autowired
    private FeeManageService feeManageService;

    @Autowired
    private UsersGmailService usersGmailService;

    @Autowired
    private FeeManageController feeManageController;

    @Autowired
    private ScheduleFeeService scheduleFeeService;



    public DueTimeFee findById(Integer id){
        return dueTimeFeeRepository.findById(id).orElse(null);
    }

    public DueTimeFee findByName(String name){
        return dueTimeFeeRepository.findByName(name).orElse(null);
    }

    public List<DueTimeFee> findAll(){
        return dueTimeFeeRepository.findAll();
    }


    //chay luc 0h moi ngay
    @Scheduled(cron ="0 0 0 * * *")
    public void updateFeePerMonth(){
        LocalDateTime now =LocalDateTime.now();

        List<FeeManage> feeManages= feeManageService.findAll();
        List<DueTimeFee> dueTimeFees = findAll();


        if(dueTimeFees == null){
            System.out.println("Cannot find the corresponding duetimefees.\n");
            return;
        }

        for(DueTimeFee dueTimeFee : dueTimeFees) {
            String name = dueTimeFee.getName();
            ScheduleFee scheduleFee = scheduleFeeService.findByName(name);
            if(scheduleFee == null || scheduleFee.getDay() != now.getDayOfMonth()){
                continue;
            }

            for (FeeManage feeManage : feeManages) {
                if (feeManage.getFeeSections() == null) {
                    continue;
                }
                List<FeeSection> feeSections = feeManage.getFeeSections();
                for (FeeSection feeSection : feeSections) {
                    if (feeSection.getName().equals(name)) {
                        feeManage.setTotalFee(feeManage.getTotalFee() + feeSection.getFeeName().getMoneyPerBlock() * feeSection.getBlockUsed());
                        feeManageRepository.save(feeManage);
                        break;
                    }

                }
            }
            dueTimeFee.setDueTime(now.plusMonths(1));
            dueTimeFee.setLastTime(now);
            dueTimeFeeRepository.save(dueTimeFee);
        }
    }

    @Transactional
    public boolean update(DueTimeFee dueTimeFee){
        if(dueTimeFee.getName()!= null && findByName(dueTimeFee.getName())!=null){
            DueTimeFee dueTimeFee1 = findByName(dueTimeFee.getName());
            dueTimeFee1.setName(dueTimeFee.getName());
            dueTimeFeeRepository.save(dueTimeFee1);
            return true;
        }
        return false;

    }

    @Transactional
    public boolean deleteById(Integer id){
        if(findById(id) != null){
            dueTimeFeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean add(DueTimeFee dueTimeFee){
        if(dueTimeFee.getName() != null && findByName(dueTimeFee.getName()) ==null && scheduleFeeService.findByName(dueTimeFee.getName()) != null){
            dueTimeFeeRepository.save(dueTimeFee);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteByName(String name){
        if(findByName(name) != null){
            dueTimeFeeRepository.deleteByName(name);
            return true;
        }
        return false;
    }

}
