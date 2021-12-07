package com.my.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTag extends SimpleTagSupport {
    private BigDecimal amount;

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();

        if (amount == null) {
            writer.println("0.00");
        } else {
            writer.println(amount.setScale(2, RoundingMode.HALF_DOWN));
        }
    }
}
