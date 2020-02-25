package service;

import client.ExchangeRateDataClient;
import client.MockExchangeRateDataClient;
import dao.ExchangeRateDao;
import dto.CurrencyCode;
import entity.ExchangeRate;
import mapper.ExchangeRateMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
                .thenReturn(List.of(exchangeRate));
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
        dto.ExchangeRate exchangeRate = exchangeRateService.getLatestExchangeRate(CurrencyCode.BITCOIN, CurrencyCode.USD);
        verify(exchangeRateDao, atLeast(1)).getLatest(CurrencyCode.BITCOIN, CurrencyCode.USD);
        assertEquals(exchangeRate.getValue(), BigDecimal.valueOf(100));
        assertEquals(exchangeRate.getSourceCurrency(), CurrencyCode.BITCOIN);
        assertEquals(exchangeRate.getTargetCurrency(), CurrencyCode.USD);
        assertEquals(exchangeRate.getValueAt(), null);
    }

    @Test
    public void testGetHistoricalData() {
        List<dto.ExchangeRate> exchangeRates = exchangeRateService.getHistoricalData(new Timestamp(currentTime), new Timestamp(currentTime),
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
