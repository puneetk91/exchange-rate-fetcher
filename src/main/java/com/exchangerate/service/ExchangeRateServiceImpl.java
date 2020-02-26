package com.exchangerate.service;

import com.exchangerate.client.ExchangeRateDataClient;
import com.exchangerate.dao.ExchangeRateDao;
import com.exchangerate.dto.CurrencyCode;
import com.exchangerate.dto.ExchangeRate;
import com.exchangerate.dto.ExchangeRateDataClientResponse;
import com.exchangerate.mapper.ExchangeRateMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.exchangerate.util.TimestampUtil.addToTimestamp;
import static org.slf4j.LoggerFactory.getLogger;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    Logger logger = getLogger(ExchangeRateServiceImpl.class);

    private final ExchangeRateDataClient exchangeRateDataClient;
    private final ExchangeRateDao exchangeRateDao;
    private final ExchangeRateMapper mapper;

    public ExchangeRateServiceImpl(ExchangeRateDataClient exchangeRateDataClient, ExchangeRateDao exchangeRateDao,
                                   ExchangeRateMapper mapper) {
        this.exchangeRateDataClient = exchangeRateDataClient;
        this.exchangeRateDao = exchangeRateDao;
        this.mapper = mapper;
    }

    @Override
    public ExchangeRate getLatestExchangeRate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        com.exchangerate.entity.ExchangeRate exchangeRate = exchangeRateDao.getLatest(sourceCurrency, targetCurrency);
        if (exchangeRate == null) {
            logger.info(String.format("We could not find the latest %s exchange rate to %s",
                    sourceCurrency, targetCurrency));
            throw new NotFoundException(String.format("We could not find the latest %s exchange rate to %s",
                    sourceCurrency, targetCurrency));
        }
        return mapper.mapToDto(exchangeRate);
    }

    @Override
    public List<ExchangeRate> getHistoricalData(Timestamp from, Timestamp to, CurrencyCode sourceCurrency,
                                                CurrencyCode targetCurrency) {
        List<com.exchangerate.entity.ExchangeRate> exchangeRateList = exchangeRateDao.getHistoricalData(from, to, sourceCurrency, targetCurrency);
        if (CollectionUtils.isEmpty(exchangeRateList)) {
            logger.info(String.format("We could not find the latest %s exchange rate to %s",
                    sourceCurrency, targetCurrency));
            throw new NotFoundException(String.format("We could not find the latest %s exchange rate to %s",
                    sourceCurrency, targetCurrency));
        }
        return mapper.mapToDto(exchangeRateList);
    }

    @Override
    public ExchangeRate refreshLatestExchangeRates(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        com.exchangerate.entity.ExchangeRate exchangeRate = refreshExchangeRate(sourceCurrency, targetCurrency);
        return mapper.mapToDto(exchangeRate);
    }

    /*
    * Here we are backfilling the data with a gap of 15 min
    * Note: We can also reset the data if already fetched for this time interval
    * */
    @Override
    public void backfillExchangeRates(CurrencyCode sourceCurrency, CurrencyCode targetCurrency, Date backfillFrom,
                                      Date backfillTill) {
        Timestamp temp = Timestamp.from(backfillFrom.toInstant());
        while (temp.before(Timestamp.from(backfillTill.toInstant()))) {
            refreshExchangeRate(sourceCurrency, targetCurrency);
            temp = addToTimestamp(temp, 15, Calendar.MINUTE);
        }
    }

    private com.exchangerate.entity.ExchangeRate refreshExchangeRate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        ExchangeRateDataClientResponse response = exchangeRateDataClient
                .getExchangeRate(Timestamp.from(Instant.now()), sourceCurrency, targetCurrency);
        com.exchangerate.entity.ExchangeRate exchangeRate = mapper.mapToEntity(response);
        exchangeRate = exchangeRateDao.createOrUpdate(exchangeRate);
        return exchangeRate;
    }

}
