package com.example.cp6_java.consumer;

import com.example.cp6_java.entity.TransactionEntity;
import com.example.cp6_java.entity.TransactionStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BankTransactionProcessor {

    private final RabbitTemplate rabbitTemplate;

   
    @Value("${rabbitmq.exchange.response}")
    private String responseExchange;

    @Value("${rabbitmq.routingkey.response}")
    private String responseRoutingKey;

    public BankTransactionProcessor(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @RabbitListener(queues = "${rabbitmq.queue.transaction}")
    public void receiveTransactionRequest(TransactionEntity transaction) {
        System.out.println("--- CONSUMIDOR - BANCO TRANQUILO ---");
        System.out.println("Recebido pedido de pagamento (Order ID: " + transaction.getOrderId() + ")");

        
        try {
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    
        TransactionStatus finalStatus;
        String responseDetails;

        if (new Random().nextDouble() < 0.70) {
            finalStatus = TransactionStatus.APPROVED;
            responseDetails = "Pagamento confirmado pela API do Banco Tranquilo.";
        } else {
            finalStatus = TransactionStatus.REJECTED;
            responseDetails = "Transação recusada por insuficiência de fundos.";
        }

    
        transaction.setStatus(finalStatus);
        transaction.setPaymentResponseDetails(responseDetails);

        System.out.println("Status simulado: " + finalStatus);

   
        System.out.println("Enviando resposta para a fila de Resposta...");
        rabbitTemplate.convertAndSend(responseExchange, responseRoutingKey, transaction);

        System.out.println("------------------------------------");
    }
}