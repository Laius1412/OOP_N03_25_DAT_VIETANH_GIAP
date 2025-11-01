package com.example.servingwebcontent.service.FinanceManagement;

import com.example.servingwebcontent.model.FinanceManagement.FinancialReport;
import com.example.servingwebcontent.repository.FinancialReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialReportService {

    private final FinancialReportRepository financialReportRepository;

    public FinancialReportService(FinancialReportRepository financialReportRepository) {
        this.financialReportRepository = financialReportRepository;
    }

    public List<FinancialReport> findAllOrderByGeneratedAtDesc() {
        return financialReportRepository.findAllByOrderByGeneratedAtDesc();
    }

    public Optional<FinancialReport> findById(Long id) {
        return financialReportRepository.findById(id);
    }

    public FinancialReport save(FinancialReport report) {
        return financialReportRepository.save(report);
    }

    public void deleteById(Long id) {
        financialReportRepository.deleteById(id);
    }
}

