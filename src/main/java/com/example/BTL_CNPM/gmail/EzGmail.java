package com.example.BTL_CNPM.gmail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * EzGmail - Gửi email dễ dàng qua Gmail API
 */
public class EzGmail {
    private static final String APPLICATION_NAME = "EzGmail";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";

    private static Gmail service;

    static {
        try {
            service = getGmailService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Gmail getGmailService() throws Exception {
        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials(final HttpTransport HTTP_TRANSPORT) throws Exception {
        InputStream in = new FileInputStream(Paths.get(CREDENTIALS_FILE_PATH).toFile());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /**
     * Gửi email không có tệp đính kèm.
     */
    public static void sendEmail(String to, String subject, String body) throws Exception {
        sendEmailWithAttachment(to, subject, body, new ArrayList<>());
    }

    /**
     * Gửi email với tệp đính kèm.
     */
    public static void sendEmailWithAttachment(String to, String subject, String body, List<File> attachments) throws Exception {
        MimeMessage email = createEmailWithAttachments(to, subject, body, attachments);
        Message message = createMessageWithEmail(email);
        service.users().messages().send("me", message).execute();
        System.out.println("Email sent successfully!");
    }

    /**
     * Tạo email với tệp đính kèm.
     */
    private static MimeMessage createEmailWithAttachments(String to, String subject, String bodyText, List<File> attachments) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress("your_email@gmail.com"));
        email.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);

        Multipart multipart = new MimeMultipart();

        // Phần nội dung email
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(bodyText);
        multipart.addBodyPart(textPart);

        // Đính kèm tệp
        for (File file : attachments) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());
            multipart.addBodyPart(attachmentPart);
        }

        email.setContent(multipart);
        return email;
    }

    /**
     * Chuyển đổi MimeMessage thành Gmail Message.
     */
    private static Message createMessageWithEmail(MimeMessage email) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}
