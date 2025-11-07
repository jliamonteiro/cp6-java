package com.example.cp6_java.controller;

import com.example.cp6_java.dtos.EmailRequestDto;
import com.example.cp6_java.entity.EmailEntity;
import com.example.cp6_java.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/enviar-email")
    public ResponseEntity<EmailEntity> sendEmail(@RequestBody @Valid EmailRequestDto filter) {
        EmailEntity emailEntity = new EmailEntity();

        BeanUtils.copyProperties(filter, emailEntity);

        emailService.sendEmail(emailEntity);

        return new ResponseEntity<>(emailEntity, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmailEntity>> getAll() {

        return ResponseEntity.ok(emailService.findAll());
    }

}
