package com.example.cp6_java.controller;

import com.example.cp6_java.dtos.PurchaseRequestDto;
import com.example.cp6_java.entity.TransactionEntity;
import com.example.cp6_java.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionEntity> initiatePurchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        try {
            
            TransactionEntity transaction = transactionService.initiatePurchase(purchaseRequestDto);

            
            return new ResponseEntity<>(transaction, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.err.println("Erro ao iniciar a compra: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}