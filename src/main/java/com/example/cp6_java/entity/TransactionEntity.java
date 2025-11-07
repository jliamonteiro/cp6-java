package com.example.cp6_java.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
@Table(name = "TB_TRANSACTIONS")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANS_SEQ")
    @SequenceGenerator(name = "TRANS_SEQ", sequenceName = "TRANS_SEQ", allocationSize = 1)
    private Long id;

    private String orderId;
    private BigDecimal amount;
    private String buyerEmail;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String paymentResponseDetails;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastUpdate = LocalDateTime.now();

    
}