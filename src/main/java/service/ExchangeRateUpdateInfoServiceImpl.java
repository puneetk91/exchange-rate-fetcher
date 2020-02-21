package service;

import dao.ExchangeRateUpdateInfoDao;
import dto.CurrencyCode;
import entity.ExchangeRateUpdateInfo;
import io.dropwizard.hibernate.UnitOfWork;

import java.sql.Timestamp;

public class ExchangeRateUpdateInfoServiceImpl {

    private final ExchangeRateUpdateInfoDao exchangeRateUpdateInfoDao;

    public ExchangeRateUpdateInfoServiceImpl(ExchangeRateUpdateInfoDao exchangeRateUpdateInfoDao) {
        this.exchangeRateUpdateInfoDao = exchangeRateUpdateInfoDao;
    }

    @UnitOfWork
    public ExchangeRateUpdateInfo createOrUpdate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency,
                                                 ExchangeRateUpdateInfo.FillType fillType, Timestamp lastUpdatedAt,
                                                 Timestamp lastUpdatedTill) {
        ExchangeRateUpdateInfo exchangeRateUpdateInfo = exchangeRateUpdateInfoDao.get(sourceCurrency, targetCurrency, fillType);
        if (exchangeRateUpdateInfo == null) {
            exchangeRateUpdateInfo = new ExchangeRateUpdateInfo(sourceCurrency, targetCurrency, lastUpdatedAt,
                    lastUpdatedTill, fillType);
        } else {
            exchangeRateUpdateInfo.setLastUpdatedAt(lastUpdatedAt);
            exchangeRateUpdateInfo.setLastUpdatedTill(lastUpdatedTill);
        }
        return exchangeRateUpdateInfoDao.createOrUpdate(exchangeRateUpdateInfo);
    }
}
