package com.exchangerate.dto;

import java.util.Date;

public class ExchangeRateCurrencyFillerConfig {

    private CurrencyCode sourceCurrency;

    private CurrencyCode targetCurrency;

    public ExchangeRateCurrencyFillerConfig() {}

    public ExchangeRateCurrencyFillerConfig(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
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

}
