package com.example.BTL_CNPM.gmail;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.*;

public class EmailSender {
    private final String senderEmail;
    private final String appPassword;
    private final Properties mailProperties;

    public EmailSender(String senderEmail, String appPassword) {
        this.senderEmail = senderEmail;
        this.appPassword = appPassword;

        // Cấu hình SMTP cho Gmail
        this.mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "587");
    }

    /**
     * Gửi email không có file đính kèm
     * @param toEmail Địa chỉ email người nhận
     * @param subject Tiêu đề email
     * @param body Nội dung email
     * @throws MessagingException Nếu có lỗi khi gửi email
     */
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        sendEmail(Collections.singletonList(toEmail), subject, body, null);
    }

    /**
     * Gửi email có file đính kèm
     * @param toEmails Danh sách email người nhận
     * @param subject Tiêu đề email
     * @param body Nội dung email
     * @param attachments Danh sách đường dẫn file đính kèm
     * @throws MessagingException Nếu có lỗi khi gửi email
     */
    public void sendEmailWithAttachment(List<String> toEmails, String subject, String body, List<String> attachments)
            throws MessagingException {
        sendEmail(toEmails, subject, body, attachments);
    }

    private void sendEmail(List<String> toEmails, String subject, String body, List<String> attachments)
            throws MessagingException {
        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));

        // Chuyển danh sách email thành mảng InternetAddress
        InternetAddress[] recipientAddresses = new InternetAddress[toEmails.size()];
        for (int i = 0; i < toEmails.size(); i++) {
            recipientAddresses[i] = new InternetAddress(toEmails.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, recipientAddresses);

        message.setSubject(subject);

        // Tạo nội dung email
        Multipart multipart = new MimeMultipart();

        // Phần nội dung text
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body);
        multipart.addBodyPart(textPart);

        // Xử lý file đính kèm nếu có
        if (attachments != null && !attachments.isEmpty()) {
            for (String filePath : attachments) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                try {
                    attachmentPart.attachFile(filePath);
                    multipart.addBodyPart(attachmentPart);
                } catch (Exception e) {
                    throw new MessagingException("Could not attach file: " + filePath, e);
                }
            }
        }

        message.setContent(multipart);
        Transport.send(message);
    }

//    public static void main(String[] args) {
//        // Ví dụ sử dụng
//        EmailSender sender = new EmailSender("caohuythinh@gmail.com", "plop alwz udsz opmu");
//
//        try {
//            // Gửi email thông thường
//            // sender.sendEmail("thinhhuycaotraistorm@gmail.com", "Test Email", "This is a test email without attachment");
//
//            // Gửi email có đính kèm
//            List<String> attachments = Arrays.asList(
//                    "src/main/resources/test.pdf"
//            );
//            sender.sendEmailWithAttachment(
//                    Arrays.asList("thinhhuycaotraistorm@gmail.com"),
//                    "Email with Attachments",
//                    "Please find attached files",
//                    attachments
//            );
//
//            System.out.println("Emails sent successfully!");
//        } catch (MessagingException e) {
//            System.err.println("Failed to send email: " + e.getMessage());
//        }
//    }
}