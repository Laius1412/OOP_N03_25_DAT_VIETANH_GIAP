package com.example.servingwebcontent.service.FinanceManagement;

import com.example.servingwebcontent.model.FinanceManagement.Transaction;
import com.example.servingwebcontent.model.FinanceManagement.TransactionType;
import com.example.servingwebcontent.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findTop10Recent() {
        return transactionRepository.findTop10ByOrderByTransactionDateDesc();
    }

    public List<Transaction> findAllOrderByDateDesc() {
        return transactionRepository.findAllByOrderByTransactionDateDesc();
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByCategoryIdOrderByDateDesc(Long categoryId) {
        return transactionRepository.findByCategoryIdOrderByTransactionDateDesc(categoryId);
    }

    public List<Transaction> findByDateBetween(LocalDate start, LocalDate end) {
        return transactionRepository.findByTransactionDateBetween(start, end);
    }

    public List<Transaction> findRecentByUserTop10(Long userId) {
        return transactionRepository.findTop10ByCreatedByIdOrderByTransactionDateDesc(userId);
    }

    public List<Transaction> findAllForUserOrderByDateDesc(Long userId) {
        return transactionRepository.findByCreatedByIdOrderByTransactionDateDesc(userId);
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction save(Transaction t) {
        return transactionRepository.save(t);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    public long deleteByCategoryId(Long categoryId) {
        return transactionRepository.deleteByCategoryId(categoryId);
    }
}

