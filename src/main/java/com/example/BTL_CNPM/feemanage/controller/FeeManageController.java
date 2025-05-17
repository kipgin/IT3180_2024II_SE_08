package com.example.BTL_CNPM.feemanage.controller;


import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.service.FeeManageService;
import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import com.example.BTL_CNPM.logfeetable.service.LogFeeTableService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/feemanage")
public class FeeManageController {

    @Autowired
    private FeeManageService feeManageService;

    @Autowired
    private LogFeeTableService logFeeTableService;

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return feeManageService.existsById(id);
    }

    @GetMapping("/check-ownerusername/{ownerusername}")
    public boolean existsByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return feeManageService.existsByOwnerUserName(ownerUserName);
    }

    @GetMapping("/check-section-of-ownerusername/{ownerusername}")
    public boolean checkSectionOfOwnerUserName(@PathVariable("ownerusername") String ownerUserName ,@RequestBody FeeSection feeSection){
        return feeManageService.checkSectionOfOwnerUserName(ownerUserName,feeSection);
    }

    @GetMapping("/get-all")
    public List<FeeManage> findAll(){
        return feeManageService.findAll();
    }

    @GetMapping("/get-one-id/{id}")
    public FeeManage getById(@PathVariable  Integer id){
        return feeManageService.getById(id);
    }

    @GetMapping("/get-one-ownerusername/{ownerusername}")
    public FeeManage getByOwnerUserName(@PathVariable("ownerusername") String ownerUserName){
        return feeManageService.getByOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-section-by-ownerusername/{ownerusername}")
    public FeeSection findSectionByOwnerUserName(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.findSectionByOwnerUserName(ownerUserName,feeSection);
    }

    @GetMapping("/export-fee-logs-csv/{ownerusername}")
    public void exportPaymentLogs(@PathVariable("ownerusername") String ownerUserName,HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        String filename = "log_fee_" + ownerUserName + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        LogFeeTable logFeeTable = logFeeTableService.findByOwnerUserName(ownerUserName);
        List<LogFeeSection> logs = logFeeTable.getLogFeeSectionList();
        try (OutputStream outputStream = response.getOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
             PrintWriter writer = new PrintWriter(osw)) {

            writer.write('\uFEFF');
            writer.println("STT,Ghi chú");

            int stt = 1;
            for (LogFeeSection log : logs) {

                writer.printf(
                        "%d,%s%n",
                        stt++,
                        log.getLogName()
                );
            }
        }
    }

    @GetMapping("/export-fee-logs-xlsx/{ownerusername}")
    public void exportToXlsx(@PathVariable("ownerusername") String ownerUserName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=fee-history.xlsx");

        LogFeeTable logFeeTable=logFeeTableService.findByOwnerUserName(ownerUserName);
        List<LogFeeSection> logs = logFeeTable.getLogFeeSectionList();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Lịch sử đóng phí");


        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("Ghi chú");

        int rowNum = 1;
        for (int i = 0; i < logs.size(); i++) {
            LogFeeSection log = logs.get(i);
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(log.getLogName());
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PutMapping("/update-id/{id}")
    public boolean updateById(@PathVariable Integer id,@RequestBody FeeManage feeManage){
        return feeManageService.updateById(id,feeManage);
    }

    @PutMapping("/update-ownerusername/{ownerusername}")
    public boolean updateByOwnerUserName(@PathVariable("ownerusername") String ownerUserName ,@RequestBody FeeManage feeManage){
        return feeManageService.updateByOwnerUserName(ownerUserName,feeManage);
    }

    @PutMapping("/update-section-of-ownerusername/{ownerusername}")
    public boolean updateSectionOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.updateSectionOfFeeManage(ownerUserName,feeSection);
    }

    //cap nhat hang thang
    @PutMapping("/update-fee/{ownerusername}")
    public Double updateFee(@PathVariable("ownerusername") String ownerUserName){
        return feeManageService.updateFee(ownerUserName);
    }

    @PutMapping("/payfeenormal-id/{id}/{fee}")
    public boolean paidFeeNormalById(@PathVariable Integer id,@PathVariable Double fee){
        return feeManageService.paidFeeNormalById(id,fee);
    }

    @PutMapping("/payfeenormal-ownerusername/{ownerUserName}/{fee}")
    public boolean paidFeeNormalByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName,@PathVariable Double fee) throws MessagingException {
        return feeManageService.paidFeeNormalByOwnerUserName(ownerUserName,fee);
    }

    @PutMapping("/online-banking-id/{id}/{fee}")
    public boolean paidFeeOnlinebyId(@PathVariable  Integer id,@PathVariable Double fee){
        return feeManageService.paidFeeOnlineById(id,fee);
    }

    @PutMapping("/online-banking-name/{ownerUserName}/{fee}")
    public boolean paidFeeOnlineByOwnerUserName(@PathVariable("ownerUserName")  String ownerUserName,@PathVariable Double fee){
        return feeManageService.paidFeeOnlineByOwnerUserName(ownerUserName,fee);
    }
    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return feeManageService.deleteById(id);
    }

    @DeleteMapping("/delete-name/{ownerUserName}")
    public boolean deleteByOwnerUserName(@PathVariable("ownerUserName") String ownerUserName){
        return feeManageService.deleteByOwnerUserName(ownerUserName);
    }

    @PostMapping("/delete-section-of-feemanage/{ownerusername}")
    public boolean deleteSectionOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.deleteSectionOfFeeManage(ownerUserName,feeSection);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feeManageService.deleteAll();
    }

    @PostMapping("/add")
    public boolean add(@RequestBody FeeManage feeManage){
        return feeManageService.add(feeManage);
    }
//    @PostMapping("/addtemp")
//    public FeeManage addtemp(@RequestBody FeeManage feeManage){
//        return feeManageService.addtemp(feeManage);
//    }

    @PostMapping("/add-section-of-feemanage/{ownerusername}")
    public boolean addSectionOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@RequestBody FeeSection feeSection){
        return feeManageService.addSectionOfFeeManage(ownerUserName,feeSection);
    }

}
