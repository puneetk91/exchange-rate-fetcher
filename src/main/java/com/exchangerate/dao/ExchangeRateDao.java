package com.exchangerate.dao;

import com.exchangerate.dto.CurrencyCode;
import com.exchangerate.entity.ExchangeRate;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;


public class ExchangeRateDao extends AbstractDAO<ExchangeRate> {

    public ExchangeRateDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public ExchangeRate createOrUpdate(ExchangeRate exchangeRate) {
        return super.persist(exchangeRate);
    }

    public ExchangeRate getLatest(CurrencyCode sourceCurrency, CurrencyCode targetCurrency) {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(ExchangeRate.class);
        Root<ExchangeRate> root = criteriaQuery.from(ExchangeRate.class);
        criteriaQuery.select(root).where(builder.equal(root.get("sourceCurrency"), sourceCurrency));
        criteriaQuery.select(root).where(builder.equal(root.get("targetCurrency"), targetCurrency));
        criteriaQuery.orderBy(builder.desc(root.get("valueAt")));

        Query query = currentSession().createQuery(criteriaQuery).setMaxResults(1);
        return (ExchangeRate) query.getSingleResult();
    }

    public List<ExchangeRate> getHistoricalData(Timestamp from, Timestamp to, CurrencyCode sourceCurrency,
                                                CurrencyCode targetCurrency) {

        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(ExchangeRate.class);
        Root<ExchangeRate> root = criteriaQuery.from(ExchangeRate.class);
        criteriaQuery.select(root).where(builder.equal(root.get("sourceCurrency"), sourceCurrency));
        criteriaQuery.select(root).where(builder.equal(root.get("targetCurrency"), targetCurrency));
        criteriaQuery.select(root).where(builder.between(root.get("valueAt"), from, to));
        criteriaQuery.orderBy(builder.desc(root.get("valueAt")));

        Query query = currentSession().createQuery(criteriaQuery);
        return query.getResultList();
    }

}
