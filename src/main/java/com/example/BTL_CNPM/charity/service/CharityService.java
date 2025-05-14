package com.example.BTL_CNPM.charity.service;


import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharityName;
import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.repository.CharityNameRepository;
import com.example.BTL_CNPM.charity.repository.CharityRepository;
import com.example.BTL_CNPM.charity.repository.CharitySectionRepository;
import com.example.BTL_CNPM.gmail.EmailSender;
import com.example.BTL_CNPM.gmail.model.users.UsersGmail;
import com.example.BTL_CNPM.gmail.repository.UsersGmailRepository;
import com.example.BTL_CNPM.household.model.Household;
import com.example.BTL_CNPM.household.repository.HouseholdRepository;
import com.example.BTL_CNPM.logcharitysection.LogCharitySection;
import com.example.BTL_CNPM.logcharitytable.LogCharityTableService;
import com.example.BTL_CNPM.resident.model.AccomStatus;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CharityService {
    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private CharityNameRepository charityNameRepository;

    @Autowired
    private CharitySectionRepository charitySectionRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private UsersGmailRepository usersGmailRepository;

    @Autowired
    private LogCharityTableService logCharityTableService;

//    @Autowired
//    private HouseholdRepository householdRepository;

    public boolean existsById(Integer id){
        return charityRepository.existsById(id);
    }

    public boolean existsByOwnerUserName(String ownerUserName){
        return charityRepository.existsByOwnerUserName(ownerUserName);
    }

    public Charity findById(Integer id){
        return charityRepository.findById(id).orElse(null);
    }

    public Charity findByOwnerUserName(String ownerUserName){
        return charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
    }
    public List<Charity> findAll(){
        return charityRepository.findAll();
    }

    public List<CharitySection> getAllSectionId(Integer id){
        return Objects.requireNonNull(charityRepository.findById(id).orElse(null)).getCharitySections();
    }

    public List<CharitySection> getAllSectionOwnerUserName(String ownerUserName){
        return Objects.requireNonNull(charityRepository.findByOwnerUserName(ownerUserName).orElse(null)).getCharitySections();
    }
    @Transactional
    public boolean deleteById(Integer id){
        if(!charityRepository.existsById(id)){
            return false;
        }
        charityRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean deleteByOwnerUsername(String ownerUserName){
        if(!charityRepository.existsByOwnerUserName(ownerUserName)){
            return false;
        }
        charityRepository.deleteByOwnerUserName(ownerUserName);
        return true;
    }

    @Transactional
    public boolean deleteSectionByOwnerUserName(String ownerUserName, CharityName charityName){
        if(!charityRepository.existsByOwnerUserName(ownerUserName) || charityName==null){
            return false;
        }
        String name=charityName.getName();
        Charity charity=charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        List<CharitySection> list =charity.getCharitySections();
        if(list.isEmpty()){
            return false;
        }
        Boolean flag=false;
        for(int i = 0 ; i < list.size();i++){
            if(list.get(i).getName().equals(name)){
                flag=true;
                CharitySection charitySection=list.get(i);
                list.remove(i);
                charitySectionRepository.delete(charitySection);
                break;
            }
        }
        if(!flag){
            return false;
        }
        charityRepository.save(charity);
        return true;
    }

    //update bi loi phan setCharitySections
    @Transactional
    public boolean updateCharity(String ownerUsername, AccomStatus accomStatus){
        if(ownerUsername == null || ownerUsername.isEmpty() || !charityRepository.existsByOwnerUserName(ownerUsername) ){
            return false;
        }
        Charity tempCharity = findByOwnerUserName(ownerUsername);
        tempCharity.setAccomStatus(accomStatus);
        charityRepository.save(tempCharity);
        return true;
    }


    @Transactional
    public boolean updateSectionOfCharity(String ownerUserName,CharityName charityName,Integer money) throws MessagingException {
        if(!charityRepository.existsByOwnerUserName(ownerUserName) || charityName == null || charityName.getName() == null || charityName.getName().isEmpty()){
            return false;
        }
        String name=charityName.getName();
        Charity existOne = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
//        existOne.setCharitySections(charity.getCharitySections());
        List<CharitySection> list = existOne.getCharitySections();
        if(list.isEmpty()){
            return false;
        }
        if( !(money instanceof Integer) || money <= 0){
            return false;
        }

        Boolean flag=false;
        for(CharitySection section : list){
            if(section.getName().equals(name)){
                section.setDonate(section.getDonate()+money);
                Integer fee=section.getDonate();
                EmailSender emailSender = new EmailSender("caohuythinh@gmail.com","plop alwz udsz opmu");
                LocalDateTime paidTime = LocalDateTime.now();
                String subject="Xác nhận đóng góp vào lúc:  " + paidTime.toString();
                String body="Số tiền cư dân vừa đóng góp là: "+ money.toString() + " VND." +"\n"
                        + "Hình thức thanh toán: Tiền mặt."+"\n"
                        +"Tên quỹ đóng góp: "+ name +"."+"\n"
                        +"Tổng số tiền hộ cư dân đã ủng hộ cho quỹ là: " + fee.toString() + " VND."+"\n"
                        +"Chúc cư dân một ngày tốt lành!";

                flag=true;
                UsersGmail userEmail = usersGmailRepository.findByUsername(ownerUserName).orElse(null);
                if(userEmail != null){
                    emailSender.sendEmail(userEmail.getEmail(),subject,body);
                }
                String logCharity= "Vào lúc: " + paidTime.toString()+" , hộ cư dân với username:"+ ownerUserName+ " đã ủng hộ "
                        + money.toString() + " VND " +"cho quỹ "+ name +".";
                LogCharitySection logCharitySection = new LogCharitySection();
                logCharitySection.setName(logCharity);
                logCharityTableService.addSectionToTable(ownerUserName,logCharitySection);
                break;
            }
        }
        if(!flag){
            return false;
        }
        charityRepository.save(existOne);
        return true;
    }

    public CharitySection findCharitySectionFromOwnerUserName(CharityName charityName,String ownerUserName){
        if(charityName == null || charityName.getName() == null || charityName.getName().isEmpty()){
            return null;
        }
        String sectionName=charityName.getName();
        if(sectionName == null || ownerUserName == null){
            return null;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return null;
        }
        List<CharitySection> tempCharitySection = charity.getCharitySections();
        for(CharitySection section : tempCharitySection){
            if(section == null || section.getName() == null){
                break;
            }
            if(section.getName().equals(sectionName)){
                return section;
            }
        }
        return null;
    }

    public boolean addSectionToOwnerUserName(CharitySection charitySection, String ownerUserName){
        if(ownerUserName == null || ownerUserName.isEmpty() || charitySection == null || charitySection.getName()==null || charitySection.getName().isEmpty()){
            return false;
        }
        if(!charityNameRepository.existsByName(charitySection.getName())){
            return false;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return false;
        }
        List<CharitySection> tempCharitySection=charity.getCharitySections();
        if(!tempCharitySection.isEmpty()) {
            for (CharitySection section : tempCharitySection) {
                if (section == null || section.getName() == null) {
                    break;
                }
                if (section.getName().equals(charitySection.getName())) {
                    return false;
                }
            }
        }
        charitySection.setDonate(0);
        charity.add(charitySection);
        charityRepository.save(charity);
        return true;
    }
    public Charity addSectionToOwnerUserNameTemp(CharitySection charitySection, String ownerUserName){
        if(ownerUserName == null || charitySection == null || charitySection.getName()==null){
            return null ;
        }
        if(!charityNameRepository.existsByName(charitySection.getName())){
            return null;
        }
        Charity charity = charityRepository.findByOwnerUserName(ownerUserName).orElse(null);
        if(charity == null){
            return charity;
        }
        List<CharitySection> tempCharitySection=charity.getCharitySections();
        if(!tempCharitySection.isEmpty()) {
            for (CharitySection section : tempCharitySection) {
                if (section == null || section.getName() == null) {
                    return charity;
                }
                if (section.getName().equals(charitySection.getName())) {
                    return charity;
                }
            }
        }
        charity.add(charitySection);
        if(1+1 == 2){
            return charity;
        }
        charityRepository.save(charity);
        return charity;
    }

    public boolean add(Charity charity){
        if(charity==null || charity.getOwnerUserName()==null || charity.getOwnerUserName().isEmpty() || charityRepository.existsByOwnerUserName(charity.getOwnerUserName()) || !householdRepository.existsByOwnerUsername(charity.getOwnerUserName())){
            return false;
        }
        charity.setCharitySections(new ArrayList<>());
        charityRepository.save(charity);
        return true;
    }

    public Charity addtemp(Charity charity){
        charityRepository.save(charity);
        return charity;
    }

}
