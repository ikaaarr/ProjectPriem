package com.example.priemkomis;


import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender extends AsyncTask<Void,Void,Void> {

    private String senderEmail;
    private String senderPassword;
    private String receiverEmail;
    private String subject;
    private String message;

    public EmailSender(String senderEmail, String senderPassword, String receiverEmail, String subject, String message) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
        this.receiverEmail = receiverEmail;
        this.subject = subject;
        this.message = String.valueOf(message);
    }

    @Override
    protected Void doInBackground(Void... params) {

        // Настройки для подключения к SMTP серверу, через который будет отправлено письмо
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // Создаем сессию для отправки писем
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Создаем письмо
        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            // Отправляем письмо
            Transport.send(emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
    }

