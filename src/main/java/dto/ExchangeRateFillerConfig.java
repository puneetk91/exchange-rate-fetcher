package dto;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExchangeRateFillerConfig {

    private List<ExchangeRateCurrencyFillerConfig> exchangeRateCurrencyFillerConfigList;
    private boolean isEnabled;
    private int concurrency;
    private int timeInterval;
    private TimeUnit timeUnit;

    public ExchangeRateFillerConfig(List<ExchangeRateCurrencyFillerConfig> exchangeRateCurrencyFillerConfigList,
                                    boolean isEnabled, int concurrency, int timeInterval, TimeUnit timeUnit) {
        this.exchangeRateCurrencyFillerConfigList = exchangeRateCurrencyFillerConfigList;
        this.isEnabled = isEnabled;
        this.concurrency = concurrency;
        this.timeInterval = timeInterval;
        this.timeUnit = timeUnit;
    }

    public List<ExchangeRateCurrencyFillerConfig> getExchangeRateCurrencyFillerConfigList() {
        return exchangeRateCurrencyFillerConfigList;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
