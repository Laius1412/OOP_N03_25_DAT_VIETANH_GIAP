package com.example.servingwebcontent.service.FinanceManagement;

import com.example.servingwebcontent.model.FinanceManagement.FinanceCategory;
import com.example.servingwebcontent.repository.FinanceCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanceCategoryService {

    private final FinanceCategoryRepository financeCategoryRepository;

    public FinanceCategoryService(FinanceCategoryRepository financeCategoryRepository) {
        this.financeCategoryRepository = financeCategoryRepository;
    }

    public List<FinanceCategory> findActive() {
        return financeCategoryRepository.findByIsActiveTrue();
    }

    public Optional<FinanceCategory> findById(Long id) {
        return financeCategoryRepository.findById(id);
    }

    public FinanceCategory save(FinanceCategory category) {
        return financeCategoryRepository.save(category);
    }

    public void deleteById(Long id) {
        financeCategoryRepository.deleteById(id);
    }
}

