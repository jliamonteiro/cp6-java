package com.example.cp6_java.service;

import com.example.cp6_java.dtos.EmailRequestDto;
import com.example.cp6_java.entity.EmailEntity;
import com.example.cp6_java.entity.enun.StatusEmail;
import com.example.cp6_java.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public EmailEntity sendEmail(EmailEntity emailEntity) {
        emailEntity.setSendDateEmail(LocalDateTime.now());

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(emailEntity.getEmailFrom());
            message.setTo(emailEntity.getEmailTo());
            message.setSubject(emailEntity.getSubject());
            message.setText(emailEntity.getText());

            emailSender.send(message);

            emailEntity.setStatusEmail(StatusEmail.SENT);


        } catch (MailException e) {
            emailEntity.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailEntity);
        }

    }


    public List<EmailEntity> findAll() {
        return emailRepository.findAll();
    }

    public Optional<EmailEntity> findById(UUID id) {
        return emailRepository.findById(id);
    }

}
