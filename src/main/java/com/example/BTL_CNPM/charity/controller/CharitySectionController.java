package com.example.BTL_CNPM.charity.controller;

import com.example.BTL_CNPM.charity.model.CharitySection;
import com.example.BTL_CNPM.charity.service.CharityNameService;
import com.example.BTL_CNPM.charity.service.CharitySectionService;
import com.example.BTL_CNPM.logcharitysection.LogCharitySection;
import com.example.BTL_CNPM.logcharitysection.LogCharitySectionService;
import com.example.BTL_CNPM.logcharitytable.LogCharityTable;
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
@RequestMapping("/charitysection")
public class CharitySectionController {
    @Autowired
    private CharitySectionService charitySectionService;

    @Autowired
    private LogCharitySectionService logCharitySectionService;
    void setCharitySectionService(CharitySectionService charitySectionService){
        this.charitySectionService=charitySectionService;
    }

    @GetMapping("/check-id/{id}")
    public boolean existsById(@PathVariable Integer id){
        return charitySectionService.existsById(id);
    }

    @GetMapping("/check-name/{name}")
    public boolean existsByName(@PathVariable("name") String name){
        return charitySectionService.existsByName(name);
    }

    @GetMapping("/get-id/{id}")
    public CharitySection findById(@PathVariable Integer id){
        return charitySectionService.findById(id);
    }

    @GetMapping("/get-name/{name}")
    public List<CharitySection> findByName(@PathVariable("name") String name){
        return charitySectionService.findByName(name);
    }

    @GetMapping("/get-all")
    public List<CharitySection> findAll(){
        return charitySectionService.findAll();
    }

    @GetMapping("/get-by-charity-id/{id}")
    public List<CharitySection> findByCharityId(@PathVariable Integer id){
        return charitySectionService.findByCharityId(id);
    }

    @GetMapping("/get-by-charity-name/{ownerUserName}")
    public List<CharitySection> findByCharityOwnerUsername(@PathVariable("ownerUserName") String ownerUserName){
        return charitySectionService.findByCharityOwnerUserName(ownerUserName);
    }

    @GetMapping("/get-one-from-owner-by-name/{name}/{ownerUserName}")
    public CharitySection findOneOfOwnerByName(@PathVariable("name") String name, @PathVariable("ownerUserName") String ownerUserName){
        return charitySectionService.findOneOfOwnerByName(name,ownerUserName);
    }

    @GetMapping("/export-all-charity-logs-csv")
    public void exportAllCharityLogs(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        String filename = "log_allcharity_" + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        List<LogCharitySection> logs= logCharitySectionService.findAll();
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

    @GetMapping("/export-all-charity-logs-xlsx")
    public void exportToXlsx(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=charity-history_all.xlsx");

        List<LogCharitySection> logs = logCharitySectionService.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Tất cả lịch sử đóng góp");


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
    //thuc chat la de cong tien
    @PutMapping("/update/{ownerUserName}")
    public boolean update(@PathVariable("ownerUserName") String ownerUserName, @RequestBody CharitySection charitySection){
        return charitySectionService.update(ownerUserName,charitySection);
    }

    @PostMapping("/create/{ownerUserName}")
    public boolean create(@PathVariable("ownerUserName") String ownerUserName,@RequestBody CharitySection charitySection){
        return charitySectionService.create(ownerUserName,charitySection);
    }

    @DeleteMapping("/delete/{ownerUserName}")
    public boolean delete(@PathVariable("ownerUserName") String ownerUserName,@RequestBody CharitySection charitySection){
        return charitySectionService.delete(ownerUserName,charitySection);
    }

    @DeleteMapping("/delete/all")
    public void deleteAll(){
        charitySectionService.deleteAll();
    }
}
