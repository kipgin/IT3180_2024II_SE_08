package com.example.BTL_CNPM.gmail;

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

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    replaceText(paragraph, entry.getKey(), entry.getValue());
                }
            }

            document.write(fos);
            System.out.println("File đã được tạo: " + outputPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceText(XWPFParagraph paragraph, String placeholder, String value) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null && text.contains(placeholder)) {
                text = text.replace(placeholder, value);
                run.setText(text, 0);
            }
        }
    }
}