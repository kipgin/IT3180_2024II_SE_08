package com.example.BTL_CNPM.feemanage.service;


import com.example.BTL_CNPM.feemanage.model.FeeName;
import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.repository.FeeNameRepository;
import com.example.BTL_CNPM.feemanage.repository.FeeSectionRepository;
import com.example.BTL_CNPM.gmail.model.users.UsersGmail;
import com.example.BTL_CNPM.gmail.repository.UsersGmailRepository;
import com.example.BTL_CNPM.household.repository.HouseholdRepository;
import com.example.BTL_CNPM.household.service.HouseholdService;
import com.example.BTL_CNPM.household.service.impl.HouseholdServiceImpl;
import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import com.example.BTL_CNPM.logfeetable.service.LogFeeTableService;
import com.example.BTL_CNPM.resident.model.AccomStatus;
import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.repository.FeeManageRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.BTL_CNPM.gmail.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.BTL_CNPM.resident.model.AccomStatus.MOVED_OUT;

@Service
public class FeeManageService {
    @Autowired
    private FeeManageRepository feeManageRepository;

    @Autowired
    private FeeNameRepository feeNameRepository;

    @Autowired
    private FeeSectionRepository feeSectionRepository;
    //get,exist,find

    @Autowired
    private FeeSectionService feeSectionService;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private UsersGmailRepository usersGmailRepository;

    @Autowired
    private LogFeeTableService logFeeTableService;


    public boolean existsById(Integer id){
        return feeManageRepository.existsById(id);
    }

    public boolean existsByOwnerUserName(String ownerUserName){
        if(ownerUserName == null || ownerUserName.isEmpty()){
            return false;
        }
        return feeManageRepository.existsByOwnerUserName(ownerUserName);
    }

    public boolean checkSectionOfOwnerUserName(String ownerUserName , FeeSection feeSection){
        if(feeSection==null || feeSection.getName().isEmpty()){
            return false;
        }
        String name=feeSection.getName();
        if(ownerUserName == null || ownerUserName.isEmpty() || name == null || name.isEmpty()){
            return false;
        }
        if(!feeManageRepository.existsByOwnerUserName(ownerUserName) || !feeNameRepository.existsByName(name)){
            return false;
        }
        FeeManage feeManage = feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        List<FeeSection> list= feeManage.getFeeSections();
        if(list.isEmpty()){
            return false;
        }
        for(int i = 0 ; i < list.size() ; i++){
            if(list.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public FeeSection findSectionByOwnerUserName(String ownerUserName,FeeSection feeSection){
        if(feeSection== null || feeSection.getName().isEmpty()){
            return null;
        }
        String name = feeSection.getName();
        if(ownerUserName == null || ownerUserName.isEmpty() || name == null || name.isEmpty()){
            return null;
        }
        if(!feeManageRepository.existsByOwnerUserName(ownerUserName) || !feeNameRepository.existsByName(name)){
            return null;
        }
        FeeManage feeManage = feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        List<FeeSection> list= feeManage.getFeeSections();
        if(list.isEmpty()){
            return null;
        }

        for(int i = 0 ; i < list.size() ; i++){
            if(list.get(i).getName().equals(name)){
                return list.get(i);
            }
        }
        return null;
    }


    public List<FeeManage> findAll(){
        return feeManageRepository.findAll();
    }

    public FeeManage getById(Integer id){
        return feeManageRepository.findById(id).orElse(null);
    }

    public FeeManage getByOwnerUserName(String ownerUserName){
        if(ownerUserName == null || ownerUserName.isEmpty()){
            return null;
        }
        return feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
    }

    //put ( update)
    @Transactional

    //chi duoc update accomStatus
    public boolean updateById(Integer id, FeeManage feeManage){
        if(!feeManageRepository.existsById(id)){
            return false;
        }
        FeeManage tempFeeManage = feeManageRepository.findById(id).orElse(null);
        if(tempFeeManage == null){
            return false;
        }

//        tempFeeManage.setArea(tempFeeManage.getArea());
        tempFeeManage.setAccom_status(feeManage.getAccom_status());
        feeManageRepository.save(tempFeeManage);
        return true;
    }

    // chi duoc update accomStatus
    @Transactional
    public boolean updateByOwnerUserName(String ownerUserName,FeeManage feeManage){
        if(!feeManageRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        FeeManage tempFeeManage=feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(tempFeeManage == null){
            return false;
        }
//        tempFeeManage.setArea(feeManage.getArea());
        if(feeManage.getTotalFee()!=null){
            tempFeeManage.setTotalFee(feeManage.getTotalFee());
        }
        if(feeManage.getTotalFee()!= null){
            tempFeeManage.setPaid(feeManage.getPaid());
        }
        tempFeeManage.setAccom_status(feeManage.getAccom_status());
        feeManageRepository.save(tempFeeManage);
        return true;
    }

    @Transactional
    public boolean updateSectionOfFeeManage(String ownerUserName,FeeSection feeSection){
        if(ownerUserName == null || ownerUserName.isEmpty() || feeSection == null || feeSection.getName().isEmpty()){
            return false;
        }
        String name=feeSection.getName();
        Double blockUsed=feeSection.getBlockUsed();
        if(!feeManageRepository.existsByOwnerUserName(ownerUserName) || !feeNameRepository.existsByName(name) || !checkSectionOfOwnerUserName(ownerUserName,feeSection)){
            return false;
        }
//        if(!checkSectionOfOwnerUserName(ownerUserName,name)){
//            return false;
//        }
        FeeManage feeManage = feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(feeManage == null || feeManage.getFeeSections().isEmpty() ){
            return false;
        }
        for(FeeSection feeSection1 : feeManage.getFeeSections()){
            if(feeSection1.getName().equals(name)){
                feeSection1.setBlockUsed(blockUsed);
                feeManageRepository.save(feeManage);
                //feeManage.updateTotalFee();
                return true;
            }
        }
        return false;
    }

    //ham update phi hang thang
    @Transactional
    public Double updateFee(String ownerUserName){
        if(ownerUserName == null || ownerUserName.isEmpty()){
            return 0.0;
        }
        FeeManage feeManage = getByOwnerUserName(ownerUserName);
        if(feeManage==null){
            return 0.0;
        }
        feeManage.updateTotalFee();
        return feeManage.getTotalFee();
    }

    @Transactional
    public boolean paidFeeNormalById(Integer id, Double fee){
        FeeManage feeManage = feeManageRepository.findById(id).orElse(null);

        if(feeManage == null || feeManage.getAccom_status()== MOVED_OUT ){
            return false;
        }
        //feeManage.updateTotalFee();
        if(feeManage.getTotalFee()==0){
            return false;
        }
        if(fee > feeManage.getTotalFee() || fee <= 0  || !(fee instanceof Double)){
            return false;
        }
        feeManage.setTotalFee(feeManage.getTotalFee() - fee);
        if(feeManage.getTotalFee()==0){
            feeManage.setPaid(true);
        }
        for(FeeSection feeSection : feeManage.getFeeSections()){
            feeSection.setBlockUsed((double) 0);
        }
        feeManageRepository.save(feeManage);
        return true;
    }

    @Transactional
    public boolean paidFeeNormalByOwnerUserName(String ownerUserName, Double fee) throws MessagingException {
        if(ownerUserName == null || ownerUserName.isEmpty()){
            return false;
        }
        if(fee < 0 || !(fee instanceof Double)){
            return false;
        }
        FeeManage feeManage = feeManageRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(feeManage == null){
            return false;
        }
//        feeManage.updateTotalFee();
//        if(feeManage.getTotalFee()==0){
//            return false;
//        }
        if(feeManage.getAccom_status()== MOVED_OUT  || fee > feeManage.getTotalFee()){
            return false;
        }
        UsersGmail email = usersGmailRepository.findByUsername(feeManage.getOwnerUserName()).orElse(null);
        if(email == null){
            return false;
        }


        feeManage.setTotalFee(feeManage.getTotalFee() - fee);
        EmailSender emailSender = new EmailSender("caohuythinh@gmail.com","plop alwz udsz opmu");
        LocalDateTime paidTime = LocalDateTime.now();
        String subject="Xác nhận nộp phí vào lúc:  " + paidTime.toString();
        String body="Số tiền cư dân vừa nộp là: "+ fee.toString() + " VND." +"\n"
                    + "Hình thức thanh toán: Tiền mặt."+"\n"
                    +"Số tiền mà hộ cư dân cần phải nộp còn lại là : "+ feeManage.getTotalFee().toString() +" VND."+"\n";

        if(feeManage.getTotalFee() == 0){
            feeManage.setPaid(true);
            body = body + "Cư dân đã thanh toán hết các khoản phí." + "\n" +"Chúc cư dân một ngày tốt lành!";
        }
        else{
            body = body + "Đề nghị cư dân thanh toán số phí còn lại sớm nhất có thể!";
        }
        for(FeeSection feeSection : feeManage.getFeeSections()){
            feeSection.setBlockUsed((double) 0);
        }
        emailSender.sendEmail(email.getEmail(), subject, body);

        String logFee = "Vào lúc: "+paidTime.toString() +", "+"hộ cư dân với username: " + feeManage.getOwnerUserName() + " đã thanh toán: "
                        +fee.toString()+ " dành cho phí bắt buộc tháng này.";
        if(feeManage.getTotalFee()==0){
            logFee = logFee + " Hộ cư dân đã thanh toán đủ.";
        }
        else{
            logFee= logFee +" Hộ cư dân còn " + feeManage.getTotalFee().toString() +" chưa được thanh toán.";
        }
//        LogFeeTable logFeeTable = logFeeTableService.findByOwnerUserName(ownerUserName);
        LogFeeSection logFeeSection = new LogFeeSection();
        logFeeSection.setLogName(logFee);
        logFeeTableService.createSectionOfTable(ownerUserName,logFeeSection);

        feeManageRepository.save(feeManage);
        return true;
    }


    //phan nay dung cho quet QR,banking
    public boolean paidFeeOnlineById(Integer id,Double fee){

        return false;
    }

    public boolean paidFeeOnlineByOwnerUserName(String ownerUserName,Double fee){
        return false;
    }



    //delete
    @Transactional
    public boolean deleteById(Integer id){
        if(feeManageRepository.existsById(id)){
            feeManageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteByOwnerUserName(String ownerUserName){
        if(feeManageRepository.existsByOwnerUserName(ownerUserName)){
            feeManageRepository.deleteByOwnerUserName(ownerUserName);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteSectionOfFeeManage(String ownerUserName,FeeSection feeSection){
        if(ownerUserName == null || ownerUserName.isEmpty() || feeSection == null || feeSection.getName().isEmpty()){
            return false;
        }
        String name=feeSection.getName();
        FeeManage feeManage = getByOwnerUserName(ownerUserName);
        if(feeManage == null || feeManage.getFeeSections().isEmpty()){
            return false;
        }
        for(FeeSection feeSection1 : feeManage.getFeeSections()){
            if(feeSection1.getName().equals(name)){
                feeSection1.setFeeManage(null);
                feeSection1.setFeeName(null);
                feeManage.getFeeSections().remove(feeSection1);
                //thuc ra k can cai nay, tai vi da co orphanRemoval = true
//                feeSection.setFeeManage(null);
                feeManageRepository.save(feeManage);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void deleteAll(){
        feeManageRepository.deleteAll();
    }

    //create

    public boolean add(FeeManage feeManage){
        if(feeManage == null ){
            return false;
        }
        String ownerUserName = feeManage.getOwnerUserName();
        if(ownerUserName == null || ownerUserName.isEmpty() || existsByOwnerUserName(ownerUserName) || !householdRepository.existsByOwnerUsername(ownerUserName) ){
            return false;
        }
        feeManage.setPaid(false);
        feeManage.setFeeSections(new ArrayList<>());
        feeManage.updateTotalFee();
        feeManageRepository.save(feeManage);

        return true;
    }

    public boolean addSectionOfFeeManage(String ownerUserName,FeeSection feeSection){
        if(ownerUserName == null || ownerUserName.isEmpty() || !feeManageRepository.existsByOwnerUserName(ownerUserName) || feeSection == null || feeSection.getName().isEmpty()){
            return false;
        }
        if(!feeNameRepository.existsByName(feeSection.getName())){
            return false;
        }
        FeeManage feeManage = getByOwnerUserName(ownerUserName);
        if(this.findSectionByOwnerUserName(ownerUserName, feeSection) != null){
            return false;
        }
        feeSection.setFeeName(feeNameRepository.findByName(feeSection.getName()).orElse(null));
        feeManage.addSection(feeSection);
        feeManageRepository.save(feeManage);
        return true;
    }


}
