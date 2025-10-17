package com.example.servingwebcontent;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinanceItem {
    public enum Type { INCOME, EXPENSE }

    private long id;
    private Type type;
    private BigDecimal amount;
    private String description;
    private LocalDate date;

    public FinanceItem() {}

    public FinanceItem(long id, Type type, BigDecimal amount, String description, LocalDate date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}


