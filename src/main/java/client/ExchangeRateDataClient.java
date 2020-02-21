package client;

import dto.CurrencyCode;
import dto.ExchangeRateDataClientResponse;

import java.sql.Timestamp;

public interface ExchangeRateDataClient {

    ExchangeRateDataClientResponse getExchangeRate(Timestamp timestamp, CurrencyCode sourceCurrency,
                                                   CurrencyCode targetCurrency);

}
