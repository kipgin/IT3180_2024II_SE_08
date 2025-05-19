package com.example.BTL_CNPM.feemanage.controller;


import com.example.BTL_CNPM.feemanage.model.FeeSection;
import com.example.BTL_CNPM.feemanage.service.FeeSectionService;
import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.example.BTL_CNPM.logfeesection.LogFeeSectionService;
import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

//    @GetMapping("/export-all-fee-logs-csv")
//    public void exportPaymentLogs(HttpServletResponse response) throws IOException {
//        response.setContentType("text/csv; charset=UTF-8");
//        String filename = "log_fee_all" + ".csv";
//        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//
//        List<LogFeeSection> logs = logFeeSectionService.findAll();
//        try (OutputStream outputStream = response.getOutputStream();
//             OutputStreamWriter osw = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
//             PrintWriter writer = new PrintWriter(osw)) {
//
//            writer.write('\uFEFF');
//            writer.println("STT,Ghi chú");
//
//            int stt = 1;
//            for (LogFeeSection log : logs) {
//
//                writer.printf(
//                        "%d,%s%n",
//                        stt++,
//                        log.getLogName()
//                );
//            }
//        }
//    }

    @GetMapping("/export-all-fee-logs-xlsx")
    public void exportToXlsx(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        String filename = "log_fee_all.docx";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);


        List<LogFeeSection> logs = logFeeSectionService.findAll();

        try (XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("BẢNG THỐNG KÊ LỊCH SỬ THU PHÍ CỦA TẤT CẢ CÁC HỘ CƯ DÂN");
            titleRun.setBold(true);
            titleRun.setFontSize(16);
            titleRun.setFontFamily("Times New Roman");

            document.createParagraph().createRun().addBreak(); // dong trong

            XWPFParagraph intro = document.createParagraph();
            XWPFRun introRun = intro.createRun();
            introRun.setFontSize(12);
            introRun.setText("Số 1, Đại Cồ Việt, quận Hai Bà Trưng");
            introRun.addBreak();
            introRun.setText("Ngày xuất bảng: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            introRun.addBreak();

            XWPFTable table = document.createTable();
            table.setWidth("100%");


            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("STT");
            headerRow.addNewTableCell().setText("Thời gian nộp");
            headerRow.addNewTableCell().setText("Số tiền đã nộp (VND)");
            headerRow.addNewTableCell().setText("Trạng thái");
            headerRow.addNewTableCell().setText("Username chủ hộ");

            // data
            int stt = 1;
            for (LogFeeSection log : logs) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(stt++));
                row.getCell(1).setText(log.getTimeCreate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                row.getCell(2).setText(log.getFeePaid().toString());
                if(log.getPaid()){
                    row.getCell(3).setText("Đã nộp đủ");
                }
                else{
                    row.getCell(3).setText("Chưa nộp đủ");
                }
                row.getCell(4).setText(log.getLogFeeTable().getOwnerUserName());
            }

            // in ra cai response
            try (ServletOutputStream out = response.getOutputStream()) {
                document.write(out);
            }
        }
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
