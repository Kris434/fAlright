package com.falright.falright.service;

import com.falright.falright.repository.EmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailInterface {
    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String from;

    public void sendEmail(String to, String subject, String content) {
        try
        {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setFrom(from);
            mail.setSubject(subject);
            mail.setText(content);

            javaMailSender.send(mail);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
