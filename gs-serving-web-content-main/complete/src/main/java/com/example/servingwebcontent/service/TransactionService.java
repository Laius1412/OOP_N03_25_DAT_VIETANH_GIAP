package com.example.servingwebcontent.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.servingwebcontent.model.Transaction;

@Service
public class TransactionService {
    private final List<Transaction> items = new ArrayList<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    public List<Transaction> findAll() { return items; }

    public Transaction findById(long id) {
        return items.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public void save(Transaction t) {
        if (t.getId() == 0) {
            t.setId(idSeq.getAndIncrement());
            items.add(t);
        }
    }

    public boolean update(long id, Transaction data) {
        Transaction t = findById(id);
        if (t == null) return false;
        if (data.getType() != null) t.setType(data.getType());
        if (data.getAmount() != null) t.setAmount(data.getAmount());
        if (data.getDescription() != null) t.setDescription(data.getDescription());
        if (data.getEventName() != null) t.setEventName(data.getEventName());
        if (data.getPayerName() != null) t.setPayerName(data.getPayerName());
        if (data.getDate() != null) t.setDate(data.getDate());
        return true;
    }

    public boolean delete(long id) {
        Transaction t = findById(id);
        if (t == null) return false;
        return items.remove(t);
    }

    public BigDecimal getBalance() {
        BigDecimal income = items.stream()
            .filter(i -> i.getType() == Transaction.Type.INCOME)
            .map(i -> i.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = items.stream()
            .filter(i -> i.getType() == Transaction.Type.EXPENSE)
            .map(i -> i.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return income.subtract(expense);
    }
}


