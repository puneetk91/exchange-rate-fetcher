package client;

import dto.CurrencyCode;
import dto.ExchangeRateDataClientResponse;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Currency;

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
