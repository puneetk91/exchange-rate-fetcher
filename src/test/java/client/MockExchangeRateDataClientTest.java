package client;


import dto.CurrencyCode;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MockExchangeRateDataClientTest {

    public static MockExchangeRateDataClient mockExchangeRateDataClient;

    @BeforeClass
    public static void setup() {
        mockExchangeRateDataClient = new MockExchangeRateDataClient();
    }

    @Test
    public void getExchangeRate() {
        BigDecimal result = mockExchangeRateDataClient.getExchangeRate(new Timestamp(System.currentTimeMillis()), CurrencyCode.USD, CurrencyCode.BITCOIN).getValue();
        assertTrue(result.compareTo(BigDecimal.valueOf(10000)) >= 0 && result.compareTo(BigDecimal.valueOf(30000)) <= 0 );
    }
}