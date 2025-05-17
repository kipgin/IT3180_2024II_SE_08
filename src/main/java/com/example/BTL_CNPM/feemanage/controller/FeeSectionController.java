package com.example.BTL_CNPM.feemanage.controller;


import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.service.FeeSectionService;
import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeesection.LogFeeSectionService;
import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import jakarta.persistence.criteria.CriteriaBuilder;
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

@RestController
@RequestMapping("/feesection")
public class FeeSectionController {
    @Autowired
    private FeeSectionService feeSectionService;

    @Autowired
    private LogFeeSectionService logFeeSectionService;

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable("id") Integer id){
        return feeSectionService.existsById(id);
    }

    @GetMapping("/check-name-of-feemanage/{ownerusername}/{name}")
    public boolean existsByNameOfFeeManange(@PathVariable("ownerusername") String ownerUserName,@PathVariable("name") String name){
        return feeSectionService.existsByNameOfFeeManange(ownerUserName,name);
    }

    @GetMapping("/find-by-id/{id}")
    public FeeSection findById(@PathVariable("id") Integer id){
        return feeSectionService.findById(id);
    }

    @GetMapping("/find-by-name-feemanage/{ownerusername}/{name}")
    public FeeSection findByNameOfFeeManage(@PathVariable("ownerusername") String ownerUserName,@PathVariable("name") String name){
        return feeSectionService.findByNameOfFeeManage(ownerUserName,name);
    }

    @GetMapping("/find-all")
    public List<FeeSection> findAll(){
        return feeSectionService.findAll();
    }

    @GetMapping("/export-all-fee-logs-csv")
    public void exportPaymentLogs(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        String filename = "log_fee_all" + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        List<LogFeeSection> logs = logFeeSectionService.findAll();
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

    @GetMapping("/export-all-fee-logs-xlsx")
    public void exportToXlsx(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=fee-history_all.xlsx");

        List<LogFeeSection> logs = logFeeSectionService.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Tất cả lịch sử nộp phí");


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
    @PutMapping("/normal-update/{id}")
    public boolean normalUpdate(@PathVariable("id")Integer id, @RequestBody FeeSection feeSection){
        return feeSectionService.normalUpdate(id,feeSection);
    }

    @PostMapping("/normalCreate")
    public boolean normalCreate(@RequestBody FeeSection feeSection){
        return feeSectionService.normalCreate(feeSection);
    }

    @DeleteMapping("/delete-id/{id}")
    public boolean deleteById(@PathVariable("id") Integer id){
        return feeSectionService.deleteById(id);
    }

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        feeSectionService.deleteAll();
    }
}
