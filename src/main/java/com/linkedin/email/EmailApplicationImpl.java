package com.linkedin.email;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailApplicationImpl implements EmailApplication {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    Environment environment;
    
    @Override
    public void sendEmail(String body) {
        try {
            // Crée un objet SimpleMailMessage
            SimpleMailMessage message = new SimpleMailMessage();

            // Adresse e-mail du destinataire
            message.setTo(environment.getProperty("SPRING_MAIL_RECEIVER"));

            // Sujet de l'e-mail
            message.setSubject("New Jobs Java at : "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // Ajoute le contenu
            message.setText(body);

            // Envoyer l'e-mail
            javaMailSender.send(message);
            log.info("E-mail envoyé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Échec de l'envoi de l'e-mail !");
        }
    }
    
    @Override
    public void sendEmail(String subject, String body) {
        try {
            // Crée un objet SimpleMailMessage
            SimpleMailMessage message = new SimpleMailMessage();

            // Adresse e-mail du destinataire
            message.setTo(environment.getProperty("SPRING_MAIL_RECEIVER"));

            // Sujet de l'e-mail
            message.setSubject(subject);

            // Ajoute le contenu
            message.setText(body);

            // Envoyer l'e-mail
            javaMailSender.send(message);
            log.info("E-mail envoyé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Échec de l'envoi de l'e-mail !");
        }
    }
}
