package com.example.servingwebcontent.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.servingwebcontent.model.Transaction;
import com.example.servingwebcontent.model.Transaction.Type;
import com.example.servingwebcontent.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public boolean update(Long id, Transaction newData) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(id);
        if (transactionOpt.isEmpty()) return false;
        
        Transaction transaction = transactionOpt.get();
        if (newData.getType() != null) transaction.setType(newData.getType());
        if (newData.getAmount() != null) transaction.setAmount(newData.getAmount());
        if (newData.getDescription() != null) transaction.setDescription(newData.getDescription());
        if (newData.getDate() != null) transaction.setDate(newData.getDate());
        if (newData.getEventName() != null) transaction.setEventName(newData.getEventName());
        if (newData.getPayerName() != null) transaction.setPayerName(newData.getPayerName());
        
        transactionRepository.save(transaction);
        return true;
    }

    public boolean delete(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAllOrderByDateDesc();
    }
    
    public List<Transaction> findByType(Type type) {
        return transactionRepository.findByType(type);
    }
    
    public List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateBetween(startDate, endDate);
    }
    
    public List<Transaction> findByEventName(String eventName) {
        return transactionRepository.findByEventNameContainingIgnoreCase(eventName);
    }
    
    public List<Transaction> findByPayerName(String payerName) {
        return transactionRepository.findByPayerNameContainingIgnoreCase(payerName);
    }
    
    public BigDecimal getTotalIncome() {
        BigDecimal total = transactionRepository.sumAmountByType(Type.INCOME);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalExpense() {
        BigDecimal total = transactionRepository.sumAmountByType(Type.EXPENSE);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getBalance() {
        BigDecimal balance = transactionRepository.calculateBalance();
        return balance != null ? balance : BigDecimal.ZERO;
    }
}