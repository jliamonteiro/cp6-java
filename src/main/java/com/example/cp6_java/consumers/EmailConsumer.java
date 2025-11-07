package com.example.cp6_java.consumer;

import com.example.cp6_java.entity.TransactionEntity;
import com.example.cp6_java.entity.EmailEntity;
import com.example.cp6_java.entity.enun.StatusEmail;
import com.example.cp6_java.entity.enun.TransactionStatus; 
import com.example.cp6_java.repository.TransactionRepository; 
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer { 

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private TransactionRepository transactionRepository; 

 
    @RabbitListener(queues = "${rabbitmq.queue.response}")
    public void listenTransactionResponse(@Payload TransactionEntity response) { 
        System.out.println("--- CONSUMIDOR DE RESPOSTA (BD/EMAIL) ---");
        System.out.println("Recebida resposta para Order ID: " + response.getOrderId());
        
  
        TransactionEntity transaction = transactionRepository.findByOrderId(response.getOrderId());
        
        if (transaction != null) {
            transaction.setStatus(response.getStatus());
            transaction.setPaymentResponseDetails(response.getPaymentResponseDetails());
         
            transactionRepository.save(transaction); 
            
           
            if (response.getStatus() == TransactionStatus.APPROVED) {
                System.out.println("Transação APROVADA. Iniciando envio de e-mail...");
                
                
                EmailEntity emailEntity = new EmailEntity();
                emailEntity.setEmailFrom("seu-email-configurado@gmail.com"); 
                emailEntity.setEmailTo(transaction.getBuyerEmail());
                emailEntity.setSubject("✅ CONFIRMAÇÃO DE PAGAMENTO - Banco Tranquilo");
                emailEntity.setText(
                    "O pagamento de R$ " + transaction.getAmount() + " para o pedido " + transaction.getOrderId() +
                    " foi **APROVADO** pelo Banco Tranquilo."
                );

             
                emailService.sendEmail(emailEntity); 
            } else {
                System.out.println("Transação REJEITADA. E-mail de confirmação não enviado.");
            }
        } else {
            System.err.println("ERRO FATAL: Transação não encontrada no BD.");
        }
    }
}