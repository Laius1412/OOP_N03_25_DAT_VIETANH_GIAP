package com.example.servingwebcontent.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.servingwebcontent.model.Transaction;
import com.example.servingwebcontent.model.Transaction.Type;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByType(Type type);
    
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Transaction> findByEventNameContainingIgnoreCase(String eventName);
    
    List<Transaction> findByPayerNameContainingIgnoreCase(String payerName);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type")
    BigDecimal sumAmountByType(@Param("type") Type type);
    
    @Query("SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE -t.amount END) FROM Transaction t")
    BigDecimal calculateBalance();
    
    @Query("SELECT t FROM Transaction t ORDER BY t.date DESC")
    List<Transaction> findAllOrderByDateDesc();
}

