package com.example.BTL_CNPM.gmail.model.otp;

import java.util.HashMap;
import java.util.Map;

import static com.example.BTL_CNPM.gmail.WordTemplateReplacer.generateDocument;

public class OTPWordForm extends OTPFormGenerator {

    public OTPWordForm(String userName) {
        super(userName);
    }

    @Override
    public String generateForm(String filePath) {
        // Đường dẫn template và file xuất ra
        String templatePath = "src/main/resources/form_word/input/otp_template.docx";
        String outputPath = "src/main/resources/form_word/output/" + userName + "-" + getFormattedTimeStampForGenerate() + ".docx";

        // Gọi hàm tạo file
        // Tạo danh sách các cặp thay thế
        Map<String, String> replacements = new HashMap<>();
        replacements.put("{{NAME}}", userName);
        replacements.put("{{OTP}}", otpCode);
        replacements.put("{{TIME}}", getFormattedTimestamp());
        // Gọi hàm tạo file
        generateDocument(templatePath, outputPath, replacements);
        return outputPath;

    }
}
