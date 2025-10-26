package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.Model.FinanceManagement.Transaction;
import com.example.servingwebcontent.Model.FinanceManagement.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCreatedByIdOrderByTransactionDateDesc(Long createdById);
    List<Transaction> findByCreatedByIdAndTransactionDateBetween(Long createdById, LocalDate startDate, LocalDate endDate);
    List<Transaction> findTop10ByCreatedByIdOrderByTransactionDateDesc(Long createdById);
    List<Transaction> findByCreatedByIdAndTransactionType(Long createdById, TransactionType transactionType);
    List<Transaction> findByCategoryIdAndCreatedByIdOrderByTransactionDateDesc(Long categoryId, Long createdById);
    List<Transaction> findByCategoryIdOrderByTransactionDateDesc(Long categoryId);
    // Admin/Finance manager views (no user filter)
    List<Transaction> findTop10ByOrderByTransactionDateDesc();
    List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
    List<Transaction> findAllByOrderByTransactionDateDesc();

    // Xóa tất cả giao dịch theo danh mục
    long deleteByCategoryId(Long categoryId);
}
