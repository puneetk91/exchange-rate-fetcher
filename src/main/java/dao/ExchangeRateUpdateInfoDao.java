package dao;

import dto.CurrencyCode;
import entity.ExchangeRateUpdateInfo;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;


import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ExchangeRateUpdateInfoDao extends AbstractDAO<ExchangeRateUpdateInfo> {


    public ExchangeRateUpdateInfoDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public ExchangeRateUpdateInfo createOrUpdate(ExchangeRateUpdateInfo exchangeRateUpdateInfo) {
        return super.persist(exchangeRateUpdateInfo);
    }

    public ExchangeRateUpdateInfo get(CurrencyCode source, CurrencyCode target, ExchangeRateUpdateInfo.FillType fillType) {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(ExchangeRateUpdateInfo.class);
        Root<ExchangeRateUpdateInfo> root = criteriaQuery.from(ExchangeRateUpdateInfo.class);
        criteriaQuery.select(root).where(builder.equal(root.get("sourceCurrency"), source));
        criteriaQuery.select(root).where(builder.equal(root.get("targetCurrency"), target));
        criteriaQuery.select(root).where(builder.equal(root.get("fillType"), fillType));

        Query query = currentSession().createQuery(criteriaQuery);
        ExchangeRateUpdateInfo exchangeRateUpdateInfo = (ExchangeRateUpdateInfo) query.getSingleResult();
        return exchangeRateUpdateInfo;
    }
}
