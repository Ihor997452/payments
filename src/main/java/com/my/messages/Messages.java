package com.my.messages;

import com.my.util.PropertyReader;
import java.util.Locale;

public class Messages {
    private final Locale locale;

    public Messages(String lang) {
        this.locale = new Locale(lang);
    }

    public String getNotLoggedInError() {
        return PropertyReader.readI18NProperties("servlet.messages.notLoggedIn", this.locale);
    }

    public String getAlreadyLoggedInError() {
        return PropertyReader.readI18NProperties("servlet.messages.alreadyLoggedIn", this.locale);
    }

    public String getEditError() {
        return PropertyReader.readI18NProperties("servlet.messages.editError", this.locale);
    }

    public String getEditSuccess() {
        return PropertyReader.readI18NProperties("servlet.messages.editSuccess", this.locale);
    }

    public String getInvalidInputError() {
        return PropertyReader.readI18NProperties("servlet.messages.invalidInput", this.locale);
    }

    public String getBasicError() {
        return PropertyReader.readI18NProperties("servlet.messages.basicError", this.locale);
    }

    public String getIncorrectCodeError() {
        return PropertyReader.readI18NProperties("servlet.messages.incorrectCode", this.locale);
    }

    public String getRegisterSuccess() {
        return PropertyReader.readI18NProperties("servlet.messages.registerSuccess", this.locale);
    }

    public String getRegisterError() {
        return PropertyReader.readI18NProperties("servlet.messages.registerError", this.locale);
    }

    public String getPasswordChangeSuccess() {
        return PropertyReader.readI18NProperties("servlet.messages.passwordChangeSuccess", this.locale);
    }

    public String getPasswordChangeError() {
        return PropertyReader.readI18NProperties("servlet.messages.passwordChangeError", this.locale);
    }

    public String getPinCodeError() {
        return PropertyReader.readI18NProperties("servlet.messages.pinCodeError", this.locale);
    }

    public String getCurrencyError() {
        return PropertyReader.readI18NProperties("servlet.messages.chooseCurrencyError", this.locale);
    }

    public String getFindProfileError() {
        return PropertyReader.readI18NProperties("servlet.messages.findProfileError", this.locale);
    }

    public String getAccountCreateSuccess() {
        return PropertyReader.readI18NProperties("servlet.messages.accountCreateSuccess", this.locale);
    }

    public String getAccountCreateError() {
        return PropertyReader.readI18NProperties("servlet.messages.accountCreateError", this.locale);
    }

    public String getUserAlreadyExistsError() {
        return PropertyReader.readI18NProperties("servlet.messages.userAlreadyExistsError", this.locale);
    }

    public String getTopUpSuccess() {
        return PropertyReader.readI18NProperties("servlet.messages.topUpSuccess", this.locale);
    }

    public String getTopUpError() {
        return PropertyReader.readI18NProperties("servlet.messages.topUpError", this.locale);
    }

    public String getPDFReceiptSuccess() {
        return PropertyReader.readI18NProperties("servlet.messages.pdfReceiptSuccess", this.locale);
    }

    public String getPDFReceiptError() {
        return PropertyReader.readI18NProperties("servlet.messages.pdfReceiptError", this.locale);
    }

    public String getTransactionSuccess() {
        return PropertyReader.readI18NProperties("servlet.messages.transactionSuccess", this.locale);
    }

    public String getTransactionError() {
        return PropertyReader.readI18NProperties("servlet.messages.transactionError", this.locale);
    }

    public String getAccountDoesNotExistError() {
        return PropertyReader.readI18NProperties("servlet.messages.accountDoesNotExistError", this.locale);
    }

    public String getInvalidAmountError() {
        return PropertyReader.readI18NProperties("servlet.messages.invalidAmount", this.locale);
    }

    public String getRecipientDoesNotExistError() {
        return PropertyReader.readI18NProperties("servlet.messages.recipientDoesNotExistError", this.locale);
    }

    public String getBlockedAccountInfo() {
        return PropertyReader.readI18NProperties("servlet.messages.blockedAccountInfo", this.locale);
    }
}

