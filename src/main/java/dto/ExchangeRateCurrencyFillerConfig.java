package dto;

import java.util.Date;

public class ExchangeRateCurrencyFillerConfig {

    private CurrencyCode sourceCurrency;

    private CurrencyCode targetCurrency;

    private boolean isBackfillEnabled;

    private Date backfillFrom;

    private Date backfillTill;

    public ExchangeRateCurrencyFillerConfig(CurrencyCode sourceCurrency, CurrencyCode targetCurrency,
                                            boolean isBackfillEnabled, Date backfillFrom, Date backfillTill) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.isBackfillEnabled = isBackfillEnabled;
        this.backfillFrom = backfillFrom;
        this.backfillTill = backfillTill;
    }

    public CurrencyCode getSourceCurrency() {
        return sourceCurrency;
    }

    public CurrencyCode getTargetCurrency() {
        return targetCurrency;
    }

    public boolean isBackfillEnabled() {
        return isBackfillEnabled;
    }

    public Date getBackfillFrom() {
        return backfillFrom;
    }

    public Date getBackfillTill() {
        return backfillTill;
    }
}
