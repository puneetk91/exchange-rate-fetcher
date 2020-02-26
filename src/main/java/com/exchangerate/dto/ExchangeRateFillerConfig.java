package com.exchangerate.dto;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExchangeRateFillerConfig {

    private List<ExchangeRateCurrencyFillerConfig> exchangeRateCurrencyFillerConfigList;
    private boolean enabled;
    private int concurrency;
    private int timeInterval;
    private TimeUnit timeUnit;

    public ExchangeRateFillerConfig() {}

    public ExchangeRateFillerConfig(List<ExchangeRateCurrencyFillerConfig> exchangeRateCurrencyFillerConfigList,
                                    boolean enabled, int concurrency, int timeInterval, TimeUnit timeUnit) {
        this.exchangeRateCurrencyFillerConfigList = exchangeRateCurrencyFillerConfigList;
        this.enabled = enabled;
        this.concurrency = concurrency;
        this.timeInterval = timeInterval;
        this.timeUnit = timeUnit;
    }

    public List<ExchangeRateCurrencyFillerConfig> getExchangeRateCurrencyFillerConfigList() {
        return exchangeRateCurrencyFillerConfigList;
    }

    public void setExchangeRateCurrencyFillerConfigList(List<ExchangeRateCurrencyFillerConfig> exchangeRateCurrencyFillerConfigList) {
        this.exchangeRateCurrencyFillerConfigList = exchangeRateCurrencyFillerConfigList;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
