package com.example.cp6_java.service;

import com.example.cp6_java.dtos.PurchaseRequestDto;
import com.example.cp6_java.entity.TransactionEntity;
import com.example.cp6_java.entity.TransactionStatus;
import com.example.cp6_java.repository.TransactionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.exchange.transaction}") private String exchangeName;
    @Value("${rabbitmq.routingkey.transaction}") private String routingKey;

    public TransactionService(TransactionRepository transactionRepository, RabbitTemplate rabbitTemplate) {
        this.transactionRepository = transactionRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public TransactionEntity initiatePurchase(PurchaseRequestDto dto) {
       
        TransactionEntity transaction = new TransactionEntity();
        transaction.setOrderId(dto.getOrderId());
        transaction.setAmount(dto.getAmount());
        transaction.setBuyerEmail(dto.getBuyerEmail());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setLastUpdate(LocalDateTime.now());

        transaction = transactionRepository.save(transaction); 

    
        System.out.println("Enviando requisição de pagamento (Order ID: " + transaction.getOrderId() + ") para o RabbitMQ...");
       
        rabbitTemplate.convertAndSend(exchangeName, routingKey, transaction);

        return transaction;
    }
}