package com.my.util;

import com.my.db.constants.Currency;
import com.my.db.entity.Account;
import com.my.db.entity.Transaction;

import java.math.BigDecimal;
import java.util.*;

public class Comparators {
    private static final Map <String, Comparator<Account>> accountComparators = new TreeMap<>();
    private static final Map <String, Comparator<Transaction>> transactionComparators = new TreeMap<>();


    static {
        accountComparators.put("balance", (o1, o2) -> {
            BigDecimal balance1 = CurrencyConverter.convert(o1.getCurrency(), Currency.USD, o1.getBalance());
            BigDecimal balance2 = CurrencyConverter.convert(o2.getCurrency(), Currency.USD, o2.getBalance());
            return balance2.compareTo(balance1);
        });
        accountComparators.put("name", Comparator.comparing(Account::getName).reversed());

        transactionComparators.put("amount", (o1, o2) -> {
            BigDecimal amount1 = CurrencyConverter.convert(o1.getCurrency(), Currency.USD, o1.getAmount());
            BigDecimal amount2 = CurrencyConverter.convert(o2.getCurrency(), Currency.USD, o2.getAmount());
            return amount2.compareTo(amount1);
        });
        transactionComparators.put("time", Comparator.comparing(Transaction::getTime).reversed());
    }


    private Comparators() {
    }

    public static Comparator<Transaction> getTransactionComparator(String name) {
        if (name == null) {
            return Comparator.comparing(Transaction::getId).reversed();
        }

        Comparator<Transaction> comparator = transactionComparators.get(name.toLowerCase(Locale.ROOT));

        if (comparator == null) {
            return Comparator.comparing(Transaction::getId).reversed();
        }

        return comparator;
    }

    public static Comparator<Account> getAccountComparator(String name) {
        if (name == null) {
            return Comparator.comparing(Account::getId).reversed();
        }

        Comparator<Account> comparator = accountComparators.get(name.toLowerCase(Locale.ROOT));

        if (comparator == null) {
            return Comparator.comparing(Account::getId).reversed();
        }

        return comparator;
    }
}

