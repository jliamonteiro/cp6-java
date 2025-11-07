package com.example.cp6_java.dtos;

import java.math.BigDecimal;
// IMPORTANTE: Adicione as anotações do Lombok se estiver usando!
// @Data, @NoArgsConstructor, @AllArgsConstructor

public class PurchaseRequestDto {
    private String orderId;
    private String buyerEmail;
    private BigDecimal amount;
    private String currency;
    private String cardNumber; // Para simulação
    private String cardHolder;
    private String expiryDate;

    // Adicione Getters e Setters aqui se não usar Lombok
}