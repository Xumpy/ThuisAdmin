package com.xumpy.documenprovider.services.implementations.mail;

import com.xumpy.thuisadmin.domain.Documenten;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SmtpEmailBuilder {
    @Value( "${mail.smtpHost}" )
    private String smtpHost;
    @Value( "${mail.smtpPort}" )
    private Integer smtpPort;
    @Value( "${mail.username}" )
    private String username;
    @Value( "${mail.password}" )
    private String password;

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text, Documenten documenten) {
        JavaMailSender javaMailSender = getJavaMailSender();

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment(documenten.getDocument_naam(), new ByteArrayResource(documenten.getDocument()));

            javaMailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
