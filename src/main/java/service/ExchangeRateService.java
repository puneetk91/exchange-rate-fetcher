package service;

import dto.CurrencyCode;
import dto.ExchangeRate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ExchangeRateService {

    ExchangeRate getLatestExchangeRate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency);

    List<ExchangeRate> getHistoricalData(Timestamp from, Timestamp to, CurrencyCode sourceCurrency,
                                         CurrencyCode targetCurrency);

    ExchangeRate refreshLatestExchangeRates(CurrencyCode sourceCurrency, CurrencyCode targetCurrency);

    void backfillExchangeRates(CurrencyCode sourceCurrency, CurrencyCode targetCurrency, Date backfillFrom,
                               Date backfillTill);

}
