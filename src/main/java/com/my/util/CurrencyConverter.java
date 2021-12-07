package com.my.util;

import com.my.db.constants.Currency;
import java.math.BigDecimal;

public class CurrencyConverter {
    private CurrencyConverter() {
    }

    public static BigDecimal convert(Currency from, Currency to, BigDecimal value) {
        return value.multiply(BigDecimal.valueOf(getRate(from, to)));
    }

    public static double getRate(Currency from, Currency to) {
        return PropertyReader.readCurrencyRateProperties(from.toString()) / PropertyReader.readCurrencyRateProperties(to.toString());
    }
}

