package com.exchangerate.client;

import com.exchangerate.dto.CurrencyCode;
import com.exchangerate.dto.ExchangeRateDataClientResponse;

import java.sql.Timestamp;

public interface ExchangeRateDataClient {

    ExchangeRateDataClientResponse getExchangeRate(Timestamp timestamp, CurrencyCode sourceCurrency,
                                                   CurrencyCode targetCurrency);

}
