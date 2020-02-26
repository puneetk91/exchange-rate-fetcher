package com.exchangerate.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExchangeRate {

    private BigDecimal value;

    private CurrencyCode sourceCurrency;

    private CurrencyCode targetCurrency;

    private Timestamp valueAt;

    public ExchangeRate(BigDecimal value, CurrencyCode sourceCurrency, CurrencyCode targetCurrency, Timestamp valueAt) {
        this.value = value;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.valueAt = valueAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CurrencyCode getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(CurrencyCode sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public CurrencyCode getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyCode targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Timestamp getValueAt() {
        return valueAt;
    }

    public void setValueAt(Timestamp valueAt) {
        this.valueAt = valueAt;
    }
}
