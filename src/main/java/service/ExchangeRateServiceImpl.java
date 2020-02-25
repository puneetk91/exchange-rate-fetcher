package service;

import client.ExchangeRateDataClient;
import dao.ExchangeRateDao;
import dto.CurrencyCode;
import dto.ExchangeRate;
import dto.ExchangeRateDataClientResponse;
import mapper.ExchangeRateMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        entity.ExchangeRate exchangeRate = exchangeRateDao.getLatest(sourceCurrency, targetCurrency);
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
        List<entity.ExchangeRate> exchangeRateList = exchangeRateDao.getHistoricalData(from, to, sourceCurrency, targetCurrency);
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
        entity.ExchangeRate exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);
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
        while (temp.before(backfillTill)) {
            exchangeRateDao.createOrUpdate(getExchangeRate(sourceCurrency, targetCurrency));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 15);
        }
    }

    private entity.ExchangeRate getExchangeRate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        ExchangeRateDataClientResponse response = exchangeRateDataClient
                .getExchangeRate(Timestamp.from(Instant.now()), sourceCurrency, targetCurrency);
        entity.ExchangeRate exchangeRate = mapper.mapToEntity(response);
        exchangeRate = exchangeRateDao.createOrUpdate(exchangeRate);
        return exchangeRate;
    }

}
