package com.exchangerate.dto;

import java.util.Date;

public class ExchangeRateCurrencyFillerConfig {

    private CurrencyCode sourceCurrency;

    private CurrencyCode targetCurrency;

    private boolean backfillEnabled;

    private Date backfillFrom;

    private Date backfillTill;

    public ExchangeRateCurrencyFillerConfig() {}

    public ExchangeRateCurrencyFillerConfig(CurrencyCode sourceCurrency, CurrencyCode targetCurrency,
                                            boolean backfillEnabled, Date backfillFrom, Date backfillTill) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.backfillEnabled = backfillEnabled;
        this.backfillFrom = backfillFrom;
        this.backfillTill = backfillTill;
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

    public boolean isBackfillEnabled() {
        return backfillEnabled;
    }

    public void setBackfillEnabled(boolean backfillEnabled) {
        this.backfillEnabled = backfillEnabled;
    }

    public Date getBackfillFrom() {
        return backfillFrom;
    }

    public void setBackfillFrom(Date backfillFrom) {
        this.backfillFrom = backfillFrom;
    }

    public Date getBackfillTill() {
        return backfillTill;
    }

    public void setBackfillTill(Date backfillTill) {
        this.backfillTill = backfillTill;
    }
}
