package com.powersoft.BANK_PROJECT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service
public class Email_Service {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    Banque_Service bqService;

    public void sendHtmlEmail(String toEmail, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
        	String emailbanque=bqService.rechercherBanque(1).getEmail();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(emailbanque); // Mettez votre adresse e-mail ici
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Le paramètre true indique que le contenu est au format HTML
            mailSender.send(message);
            System.out.println("Mail sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.out.println("Failed to send mail to " + toEmail + ": " + e.getMessage());
        }
    }
}
