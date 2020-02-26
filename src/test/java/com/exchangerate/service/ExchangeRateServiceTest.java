package com.exchangerate.service;

import com.exchangerate.client.ExchangeRateDataClient;
import com.exchangerate.client.MockExchangeRateDataClient;
import com.google.common.collect.Lists;
import com.exchangerate.dao.ExchangeRateDao;
import com.exchangerate.dto.CurrencyCode;
import com.exchangerate.dto.ExchangeRateDataClientResponse;
import com.exchangerate.entity.ExchangeRate;
import com.exchangerate.mapper.ExchangeRateMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ExchangeRateServiceTest {

    public static ExchangeRateDataClient exchangeRateDataClient;
    public static ExchangeRateDao exchangeRateDao;
    public static ExchangeRateMapper exchangeRateMapper;
    public static ExchangeRateService exchangeRateService;

    static long currentTime = System.currentTimeMillis();
    static ExchangeRate exchangeRate = ExchangeRate.builder()
            .sourceCurrency(CurrencyCode.BITCOIN)
            .targetCurrency(CurrencyCode.USD)
            .value(BigDecimal.valueOf(100))
            .build();
    static ExchangeRateDataClientResponse response = new ExchangeRateDataClientResponse(BigDecimal.valueOf(200),
            CurrencyCode.BITCOIN, CurrencyCode.USD, new Timestamp(currentTime));

    @BeforeClass
    public static void setup() {
        exchangeRateDataClient = mock(MockExchangeRateDataClient.class);
        exchangeRateDao = mock(ExchangeRateDao.class);
        exchangeRateMapper = new ExchangeRateMapper();
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateDataClient, exchangeRateDao,
                exchangeRateMapper);

        when(exchangeRateDao.getLatest(CurrencyCode.BITCOIN, CurrencyCode.USD))
                .thenReturn(exchangeRate);
        when(exchangeRateDao.getHistoricalData(new Timestamp(currentTime), new Timestamp(currentTime), CurrencyCode.BITCOIN, CurrencyCode.USD))
                .thenReturn(Lists.newArrayList(exchangeRate));

        when(exchangeRateDataClient.getExchangeRate(any(Timestamp.class), any(CurrencyCode.class), any(CurrencyCode.class)))
            .thenReturn(response);
    }

    @Test(expected = NotFoundException.class)
    public void testExceptionGetLatestExchangeRate() {
        exchangeRateService.getLatestExchangeRate(CurrencyCode.USD, CurrencyCode.BITCOIN);
    }

    @Test(expected = NotFoundException.class)
    public void testExceptionGetHistoricalData() {
        exchangeRateService.getHistoricalData(new Timestamp(currentTime), new Timestamp(currentTime), CurrencyCode.USD, CurrencyCode.BITCOIN);
    }

    @Test
    public void testGetLatestExchangeRate() {
        com.exchangerate.dto.ExchangeRate exchangeRate = exchangeRateService.getLatestExchangeRate(CurrencyCode.BITCOIN, CurrencyCode.USD);
        verify(exchangeRateDao, atLeast(1)).getLatest(CurrencyCode.BITCOIN, CurrencyCode.USD);
        assertEquals(exchangeRate.getValue(), BigDecimal.valueOf(100));
        assertEquals(exchangeRate.getSourceCurrency(), CurrencyCode.BITCOIN);
        assertEquals(exchangeRate.getTargetCurrency(), CurrencyCode.USD);
        assertEquals(exchangeRate.getValueAt(), null);
    }

    @Test
    public void testGetHistoricalData() {
        List<com.exchangerate.dto.ExchangeRate> exchangeRates = exchangeRateService.getHistoricalData(new Timestamp(currentTime), new Timestamp(currentTime),
                CurrencyCode.BITCOIN, CurrencyCode.USD);
        verify(exchangeRateDao, atLeast(1)).getHistoricalData(new Timestamp(currentTime), new Timestamp(currentTime),
                CurrencyCode.BITCOIN, CurrencyCode.USD);
        assertEquals(exchangeRates.size(), 1);
        assertEquals(exchangeRates.get(0).getValue(), BigDecimal.valueOf(100));
        assertEquals(exchangeRates.get(0).getSourceCurrency(), CurrencyCode.BITCOIN);
        assertEquals(exchangeRates.get(0).getTargetCurrency(), CurrencyCode.USD);
        assertEquals(exchangeRates.get(0).getValueAt(), null);
    }

    @Test
    public void testRefreshLatestExchangeRates() {

    }

    @Test
    public void testBackfillExchangeRates() {
    }
}
