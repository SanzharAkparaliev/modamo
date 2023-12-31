package com.madoma.service.impl;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    public boolean sendEmail(String subject,String message,String to){
        boolean f = false;
        String from = "1804.01010@manas.edu.kg";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("modamo342@gmail.com","ngkuijquriacqukm");
            }
        });

        session.setDebug(true);
        MimeMessage m = new MimeMessage(session);

        try {
            m.setFrom(from);
            m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            m.setSubject(subject,"UTF-8");
            m.setText(message);
            Transport.send(m);
            f=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;

    }
}
