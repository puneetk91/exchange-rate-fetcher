package service;

import client.ExchangeRateDataClient;
import dao.ExchangeRateDao;
import dto.CurrencyCode;
import dto.ExchangeRate;
import dto.ExchangeRateDataClientResponse;
import io.dropwizard.hibernate.UnitOfWork;
import mapper.ExchangeRateMapper;
import org.apache.commons.collections4.CollectionUtils;

import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateDataClient exchangeRateDataClient;
    private final ExchangeRateDao exchangeRateDao;
    private final ExchangeRateMapper mapper;
    private final ExchangeRateUpdateInfoServiceImpl exchangeRateUpdateInfoService;

    public ExchangeRateServiceImpl(ExchangeRateDataClient exchangeRateDataClient, ExchangeRateDao exchangeRateDao,
                                   ExchangeRateMapper mapper, ExchangeRateUpdateInfoServiceImpl exchangeRateUpdateInfoService) {
        this.exchangeRateDataClient = exchangeRateDataClient;
        this.exchangeRateDao = exchangeRateDao;
        this.mapper = mapper;
        this.exchangeRateUpdateInfoService = exchangeRateUpdateInfoService;
    }

    @Override
    @UnitOfWork
    public ExchangeRate getLatestExchangeRate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        entity.ExchangeRate exchangeRate = exchangeRateDao.getLatest(sourceCurrency, targetCurrency);
        if (exchangeRate == null)
            throw new NotFoundException(String.format("We could not find the latest %s exchange rate to %s",
                    sourceCurrency, targetCurrency));
        return mapper.mapToDto(exchangeRate);
    }

    @Override
    @UnitOfWork
    public List<ExchangeRate> getHistoricalData(Timestamp from, Timestamp to, CurrencyCode currencyCode) {
        List<entity.ExchangeRate> exchangeRateList = exchangeRateDao.getHistoricalData(from, to, currencyCode);
        if (CollectionUtils.isEmpty(exchangeRateList)) {
            throw new NotFoundException("We could not find the historical Bitcoin exchange rate");
        }
        return mapper.mapToDto(exchangeRateList);
    }

    @Override
    @UnitOfWork
    public ExchangeRate refreshLatestExchangeRates(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        entity.ExchangeRate exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);
        return mapper.mapToDto(exchangeRate);
    }

    /*
    * Note: We can also reset the data if already fetched for this time interval
    * */
    @Override
    @UnitOfWork
    public void backfillExchangeRates(CurrencyCode sourceCurrency, CurrencyCode targetCurrency, Date backfillFrom,
                                      Date backfillTill) {
        Timestamp temp = Timestamp.from(backfillFrom.toInstant());
        while (temp.before(backfillTill)) {
            exchangeRateDao.createOrUpdate(getExchangeRate(sourceCurrency, targetCurrency));
            //TODO : update exchange update info
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
