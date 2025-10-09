package com.example.servingwebcontent.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    public enum Type { INCOME, EXPENSE }

    private long id;
    private Type type;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private String eventName; // tên sự kiện
    private String payerName; // người nộp

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getPayerName() { return payerName; }
    public void setPayerName(String payerName) { this.payerName = payerName; }
}


