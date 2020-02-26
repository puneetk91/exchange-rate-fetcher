package com.exchangerate.client;

import com.exchangerate.dto.CurrencyCode;
import com.exchangerate.dto.ExchangeRateDataClientResponse;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
* This is the com.exchangerate.client to fetch data from 3rd party or any server for exchange rates
* */
public class MockExchangeRateDataClient implements ExchangeRateDataClient {

    @Override
    public ExchangeRateDataClientResponse getExchangeRate(Timestamp timestamp, CurrencyCode source, CurrencyCode target) {
        return new ExchangeRateDataClientResponse(BigDecimal.valueOf(randomIntFromInterval(10000, 30000)), source, target,
                timestamp);
    }

    private double randomIntFromInterval(double min, double max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }

}
