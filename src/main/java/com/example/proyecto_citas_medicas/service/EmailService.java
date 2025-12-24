package com.example.proyecto_citas_medicas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private final JavaMailSender mailSender;

    @Value("${frontend.reset-password.url}")
    private String resetPasswordUrl;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendPasswordResetEmail(String to, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("edu09085@gmail.com");
            mailMessage.setTo(to);
            mailMessage.setSubject("Restablecer Contraseña");
            String resetUrl = resetPasswordUrl + "?token=" + token;
            mailMessage.setText("Entra al siguiente link para restablecer tu contraseña: "+ resetUrl);
            mailSender.send(mailMessage);
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:" + className + ":" + methodName + " -> " + e.getMessage());
        }
    }
}
