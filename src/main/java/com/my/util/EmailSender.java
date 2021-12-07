package com.my.util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.my.db.entity.Account;
import com.my.db.entity.Request;
import org.apache.log4j.Logger;

public class EmailSender {
    private static final Logger log = Logger.getLogger(EmailSender.class);

    private EmailSender() {
    }

    private static void sendEmail(String to, String subject, String messageText) {
        new Worker(to, subject, messageText).start();
    }

    public static void sendProfileUnblockMessage(String to) {
        String subject = "Profile Block";
        String message = "Your profile has been unblocked";
        sendEmail(to, subject, message);
    }

    public static void sendProfileBlockMessage(String to) {
        String subject = "Profile Block";
        String message = "Your profile has been blocked";
        sendEmail(to, subject, message);
    }

    public static void sendAccountUnblockMessage(String to, Account account) {
        String subject = "Account Block";
        String message = "Your account " + account.getId() + "has been unblocked";
        sendEmail(to, subject, message);
    }

    public static void sendAccountBlockMessage(String to, Account account) {
        String subject = "Account Block";
        String message = "Your account - " + account.getId() + " has been blocked";
        sendEmail(to, subject, message);
    }

    public static void sendRequestDisapprovedMessage(String to, Request request) {
        String subject = "Request Disapproved";
        String message = "Your request - " + request.getId() + " has been disapproved";
        sendEmail(to, subject, message);
    }

    public static void sendVerificationCode(String to, int code) {
        String subject = "Verification Code";
        String message = "Verification Code" + System.lineSeparator() + code + System.lineSeparator();
        sendEmail(to, subject, message);
    }

    public static void sendNewPassword(String to, String password) {
        String subject = "New Password";
        String message = "Your new password" + System.lineSeparator() + password + System.lineSeparator();
        sendEmail(to, subject, message);
    }

    private static class Worker extends Thread {
        private final String to;
        private final String subject;
        private final String messageText;

        Worker(String to, String subject, String message) {
            this.to = to;
            this.subject =subject;
            this.messageText = message;
        }

        @Override
        public void run() {
            String from = "newmailtry43@gmail.com";
            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("newmailtry43@gmail.com", "123paSSword123");
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(messageText);
                Transport.send(message);
            } catch (Exception e) {
                log.error(e); log.error(StackTrace.getStackTrace(e));
            }
        }
    }
}

