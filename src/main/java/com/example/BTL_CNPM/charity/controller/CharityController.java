package com.example.BTL_CNPM.charity.controller;

import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharityName;
import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.repository.CharityRepository;
import com.example.BTL_CNPM.charity.service.CharityService;

import com.example.BTL_CNPM.logcharitysection.LogCharitySection;
import com.example.BTL_CNPM.logcharitytable.LogCharityTable;
import com.example.BTL_CNPM.logcharitytable.LogCharityTableService;
import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import com.example.BTL_CNPM.resident.model.AccomStatus;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/charity")
public class CharityController {
    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private LogCharityTableService logCharityTableService;

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return charityService.existsById(id);
    }

    @GetMapping("/check-ownerusername/{ownerUserName}")
    public boolean existsByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-charity-id/{id}")
    public Charity findById(@PathVariable Integer id){
        return charityService.findById(id);
    }

    @GetMapping("/get-charity-ownerusername/{ownerUserName}")
    public Charity findByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.findByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-all")
    public List<Charity> findAll(){
        return charityService.findAll();
    }


    //truoc khi goi getAllSection Id/OwnerUserName thi can phai check xem id/ownerUserName co ton tai hay khong
    @GetMapping ("/get-all-section-id/{id}")
    public List<CharitySection> getAllSectionId(@PathVariable Integer id){
        return charityService.getAllSectionId(id);
    }

    @GetMapping("/get-all-section-ownerusername/{ownerUserName}")
    public List<CharitySection> getAllSectionOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.getAllSectionOwnerUserName(ownerUserName);
    }


    @GetMapping("/get-section-from-ownerusername/{ownerUserName}")
    public CharitySection findCharitySectionFromOwnerUserName(@RequestBody CharityName charityName, @PathVariable("ownerUserName") String ownerUserName){
        return charityService.findCharitySectionFromOwnerUserName(charityName,ownerUserName);
    }

    @GetMapping("/export-charity-logs-csv/{ownerusername}")
    public void exportCharityLogs(@PathVariable("ownerusername") String ownerUserName, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        String filename = "log_charity_" + ownerUserName + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        LogCharityTable logCharityTable =logCharityTableService.findByOwnerUserName(ownerUserName);
        List<LogCharitySection> logs= logCharityTable.getLogCharitySections();
        try (OutputStream outputStream = response.getOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
             PrintWriter writer = new PrintWriter(osw)) {

            writer.write('\uFEFF');
            writer.println("STT,Ghi chú");

            int stt = 1;
            for (LogCharitySection log : logs) {

                writer.printf(
                        "%d,%s%n",
                        stt++,
                        log.getName()
                );
            }
        }
    }

    @GetMapping("/export-charity-logs-xlsx/{ownerusername}")
    public void exportToXlsx(@PathVariable("ownerusername") String ownerUserName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=charity-history.xlsx");

        LogCharityTable logCharityTable = logCharityTableService.findByOwnerUserName(ownerUserName);
        List<LogCharitySection> logs = logCharityTable.getLogCharitySections();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Lịch sử đóng góp");


        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("Ghi chú");

        int rowNum = 1;
        for (int i = 0; i < logs.size(); i++) {
            LogCharitySection log = logs.get(i);
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(log.getName());
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }


    @PutMapping("/update-charity/{ownerusername}/{accomStatus}")
    public boolean updateCharity(@PathVariable("ownerusername") String ownerUserName, @PathVariable AccomStatus accomStatus){
        return charityService.updateCharity(ownerUserName,accomStatus);
    }

    //update bi loi phan setCharitySections
    @PutMapping("/update-section-of-charity/{ownerUserName}/{money}")
    public boolean updateSectionOfCharity(@PathVariable("ownerUserName")String ownerUserName,@RequestBody CharityName charityName,@PathVariable("money") Integer money) throws MessagingException {
        return charityService.updateSectionOfCharity(ownerUserName,charityName,money);
    }

    @PostMapping("/add-section-to-ownerusername/{ownerUserName}")
    public boolean addSectionToOwnerUserName(@RequestBody CharitySection charitySection,@PathVariable("ownerUserName") String ownerUserName){
        return charityService.addSectionToOwnerUserName(charitySection,ownerUserName);
    }

    //add-section-ownerusername debug
    @PostMapping("/add-section-ownerusername-temp/{ownerUserName}")
    public Charity addSectionToOwnerUserNameTemp(@RequestBody CharitySection charitySection,@PathVariable("ownerUserName") String ownerUserName){
        return charityService.addSectionToOwnerUserNameTemp(charitySection,ownerUserName);
    }


    @PostMapping("/add")
    public boolean add(@RequestBody Charity charity){
        return charityService.add(charity);
    }

    //add de debug
    @PostMapping("/addtemp")
    public Charity addtemp(@RequestBody Charity charity){
        return charityService.addtemp(charity);
    }

    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return charityService.deleteById(id);
    }

    @DeleteMapping("/delete-ownerusername/{ownerUserName}")
    public boolean deleteByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return charityService.deleteByOwnerUsername(ownerUserName);
    }
    @DeleteMapping("/delete-section-of-ownerusername/{ownerusername}")
    public boolean deleteSectionOfOwnerUserName(@PathVariable("ownerusername") String ownerUserName, @RequestBody CharityName charityName){
        return charityService.deleteSectionByOwnerUserName(ownerUserName,charityName);
    }
}
