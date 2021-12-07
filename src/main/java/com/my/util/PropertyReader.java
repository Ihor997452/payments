package com.my.util;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class PropertyReader {
    private static final Logger log = Logger.getLogger(PropertyReader.class);

    private PropertyReader() {
    }

    public static String readPoolProperties(String property) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("pool");
            return bundle.getString(property);
        } catch (Exception e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            throw new IllegalStateException("Cannot load pool properties");
        }
    }

    public static double readCurrencyRateProperties(String property) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("currencyRates");
            return Double.parseDouble(bundle.getString(property));
        } catch (Exception e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            throw new IllegalStateException("Cannot load currency rates");
        }
    }

    public static String readI18NProperties(String property, Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("locale", locale);
            return bundle.getString(property).toLowerCase();
        } catch (MissingResourceException e) {
            log.error(e); log.error(StackTrace.getStackTrace(e));
            return "No Such Property";
        }
    }
}
