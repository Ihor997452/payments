package com.my.db.entity;

import com.my.db.constants.Currency;
import com.my.db.constants.Status;
import java.math.BigDecimal;
import java.sql.Date;

public class Account extends Entity {
    private long owner;
    private Status status;
    private Currency currency;
    private String name;
    private int pinCode;
    private BigDecimal balance;
    private int cvv;
    private Date expirationDate;

    public long getOwner() {
        return this.owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPinCode() {
        return this.pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getCvv() {
        return this.cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String toString() {
        return "Account{id=" + this.id + ",owner=" + this.owner + ", status=" + this.status + ", currency=" + this.currency + ", name='" + this.name + '\'' + ", pinCode=" + this.pinCode + ", balance=" + this.balance + ", cvv=" + this.cvv + ", expirationDate=" + this.expirationDate + '}';
    }
}
