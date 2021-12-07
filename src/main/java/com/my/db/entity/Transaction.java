package com.my.db.entity;

import com.my.db.constants.Currency;
import com.my.db.constants.TransactionStatus;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction extends Entity {
    private long from;
    private long to;
    private TransactionStatus status;
    private Currency currency;
    private Timestamp time;
    private BigDecimal amount;
    private String message;

    public long getFrom() {
        return this.from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return this.to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "Transaction{id=" + this.id + ", from=" + this.from + ", to=" + this.to + ", status=" + this.status + ", time=" + this.time + ", amount=" + this.amount + ", comment='" + this.message + '\'' + '}';
    }
}

