package com.spring.shop.sendEmail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String to, String sub, String txt) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom(username);
        msg.setTo(to);
        msg.setSubject(sub);
        msg.setText(txt);

        mailSender.send(msg);
    }
}
