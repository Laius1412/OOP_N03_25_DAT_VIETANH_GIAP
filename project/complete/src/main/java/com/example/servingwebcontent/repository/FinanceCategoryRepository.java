package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.FinanceManagement.FinanceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceCategoryRepository extends JpaRepository<FinanceCategory, Long> {
    List<FinanceCategory> findByIsActiveTrue();
    List<FinanceCategory> findByTypeAndIsActiveTrue(com.example.servingwebcontent.model.FinanceManagement.TransactionType type);
}
