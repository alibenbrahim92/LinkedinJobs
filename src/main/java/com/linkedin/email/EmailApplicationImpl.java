package com.linkedin.email;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailApplicationImpl implements EmailApplication {

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    public String sendEmail(String body) {
        try {
            // Crée un objet SimpleMailMessage
            SimpleMailMessage message = new SimpleMailMessage();

            // Adresse e-mail de l'expéditeur
//            message.setFrom("alibenbrahim92u@gmail.com");

            // Adresse e-mail du destinataire
            message.setTo("alibenbrahim92u@gmail.com");

            // Sujet de l'e-mail
            message.setSubject("New Jobs Java at : "+LocalDateTime.now());

            // Ajoute le contenu
            message.setText(body);

            // Envoyer l'e-mail
            javaMailSender.send(message);
            log.info("E-mail envoyé avec succès !");
            return "E-mail envoyé avec succès !";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Échec de l'envoi de l'e-mail !");
            return "Échec de l'envoi de l'e-mail : " + e.getMessage();
        }
    }
    
    @Override
    public String sendEmail(String subject, String body) {
        try {
            // Crée un objet SimpleMailMessage
            SimpleMailMessage message = new SimpleMailMessage();

            // Adresse e-mail de l'expéditeur
//            message.setFrom("alibenbrahim92u@gmail.com");

            // Adresse e-mail du destinataire
            message.setTo("alibenbrahim92u@gmail.com");

            // Sujet de l'e-mail
            message.setSubject(subject);

            // Ajoute le contenu
            message.setText(body);

            // Envoyer l'e-mail
            javaMailSender.send(message);
            log.info("E-mail envoyé avec succès !");
            return "E-mail envoyé avec succès !";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Échec de l'envoi de l'e-mail !");
            return "Échec de l'envoi de l'e-mail : " + e.getMessage();
        }
    }
}
