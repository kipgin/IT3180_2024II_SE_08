package com.example.BTL_CNPM.gmail.wordtemplate;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class WordTemplateReplacer {

    public static void generateDocument(String templatePath, String outputPath, Map<String, String> replacements) {
        try (FileInputStream fis = new FileInputStream(new File(templatePath));
             XWPFDocument document = new XWPFDocument(fis);
             FileOutputStream fos = new FileOutputStream(new File(outputPath))) {

            // Lặp qua tất cả các đoạn văn trong file Word
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    replaceText(paragraph, entry.getKey(), entry.getValue());
                }
            }

            // Ghi file mới
            document.write(fos);
            System.out.println("✅ File đã được tạo: " + outputPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm thay thế nội dung trong đoạn văn
    private static void replaceText(XWPFParagraph paragraph, String placeholder, String value) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null && text.contains(placeholder)) {
                text = text.replace(placeholder, value);
                run.setText(text, 0);
            }
        }
    }

//    // Hàm format thời gian hiện tại
//    private static String getFormattedTimestamp() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return LocalDateTime.now().format(formatter);
//    }
//
//    // Hàm tạo mã OTP ngẫu nhiên
//    public static String generateOTP(int length) {
//        StringBuilder otp = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            otp.append((int) (Math.random() * 10)); // Số từ 0-9
//        }
//        return otp.toString();
//    }

//    public static void main(String[] args) {
//        // Đường dẫn template và file xuất ra
//        String templatePath = "src/main/resources/form_word/input/otp_template.docx";
//        String outputPath = "src/main/resources/form_word/output/otp_nguyenvana.docx";
//
//        // Tạo mã OTP
//        String userName = "Nguyễn Văn A";
//        String otpCode = generateOTP(6);
//
//        // Gọi hàm tạo file
//        // Tạo danh sách các cặp thay thế
//        Map<String, String> replacements = new HashMap<>();
//        replacements.put("{{NAME}}", "Nguyễn Văn A");
//        replacements.put("{{OTP}}", generateOTP(6));
//        replacements.put("{{TIME}}", getFormattedTimestamp());
//        replacements.put("{{EMAIL}}", "nguyenvana@gmail.com"); // Thêm bất kỳ biến nào
//
//        // Gọi hàm tạo file
//        generateDocument(templatePath, outputPath, replacements);
//    }
}