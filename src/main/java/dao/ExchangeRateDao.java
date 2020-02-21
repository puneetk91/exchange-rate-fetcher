package dao;

import dto.CurrencyCode;
import entity.ExchangeRate;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
import java.util.List;


public class ExchangeRateDao extends AbstractDAO<ExchangeRate> {

    public ExchangeRateDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public ExchangeRate createOrUpdate(ExchangeRate exchangeRate) {
        return super.persist(exchangeRate);
    }
/*
* TODO : remove criteria
* */
    public ExchangeRate getLatest(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        Criteria criteria = currentSession().createCriteria(ExchangeRate.class);
        criteria.add(Restrictions.eq("source_currency", sourceCurrency));
        criteria.add(Restrictions.eq("target_currency", targetCurrency));
        criteria.addOrder(Order.desc("value_at"));
        criteria.setMaxResults(1);
        return (ExchangeRate) criteria.uniqueResult();
    }

    public List<ExchangeRate> getHistoricalData(Timestamp from, Timestamp to, CurrencyCode currencyCode) {
        Criteria criteria = currentSession().createCriteria(ExchangeRate.class);
        criteria.add(Restrictions.between("value_at", from, to));
        criteria.add(Restrictions.eq("currency_code", currencyCode));
        return criteria.list();
    }

}
