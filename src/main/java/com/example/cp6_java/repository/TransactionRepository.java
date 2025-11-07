package com.example.cp6_java.repository;

import com.example.cp6_java.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    // Método de busca útil para o Consumidor da Resposta
    TransactionEntity findByOrderId(String orderId);
}