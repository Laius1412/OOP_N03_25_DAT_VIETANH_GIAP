package com.example.servingwebcontent;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FinanceService {

    private final List<FinanceItem> items = new ArrayList<>();
    private long nextId = 1L;

    public FinanceService() {
        // seed sample data
        add(FinanceItem.Type.INCOME, new BigDecimal("1000000"), "Nộp quỹ", LocalDate.now());
        add(FinanceItem.Type.EXPENSE, new BigDecimal("200000"), "Mua nước", LocalDate.now());
    }

    public List<FinanceItem> findAll() {
        return Collections.unmodifiableList(items);
    }

    public void add(FinanceItem.Type type, BigDecimal amount, String description, LocalDate date) {
        FinanceItem item = new FinanceItem(nextId++, type, amount, description, date);
        items.add(item);
    }

    public void deleteById(long id) {
        items.removeIf(i -> i.getId() == id);
    }

    public BigDecimal getBalance() {
        BigDecimal income = items.stream()
                .filter(i -> i.getType() == FinanceItem.Type.INCOME)
                .map(FinanceItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = items.stream()
                .filter(i -> i.getType() == FinanceItem.Type.EXPENSE)
                .map(FinanceItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return income.subtract(expense);
    }
}


