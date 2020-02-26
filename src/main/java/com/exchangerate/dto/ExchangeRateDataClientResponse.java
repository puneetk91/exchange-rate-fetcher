package com.exchangerate.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExchangeRateDataClientResponse {

    /*
    * Keeping it simple for now
    * to return only one value for given currency code
    * at a given time.
    * */
    private BigDecimal value;
    private CurrencyCode sourceCurrency;
    private CurrencyCode targetCurrency;
    private Timestamp valueAt;

    public ExchangeRateDataClientResponse(BigDecimal value, CurrencyCode sourceCurrency, CurrencyCode targetCurrency,
                                          Timestamp valueAt) {
        this.value = value;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.valueAt = valueAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public CurrencyCode getSourceCurrency() {
        return sourceCurrency;
    }

    public CurrencyCode getTargetCurrency() {
        return targetCurrency;
    }

    public Timestamp getValueAt() {
        return valueAt;
    }
}
