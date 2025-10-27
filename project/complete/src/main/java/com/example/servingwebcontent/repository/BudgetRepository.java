package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.Model.FinanceManagement.Budget;
import com.example.servingwebcontent.Model.FinanceManagement.BudgetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByCreatedByIdOrderByCreatedAtDesc(Long createdById);
    List<Budget> findByCreatedByIdAndStatus(Long createdById, BudgetStatus status);
    List<Budget> findAllByOrderByCreatedAtDesc();
}
