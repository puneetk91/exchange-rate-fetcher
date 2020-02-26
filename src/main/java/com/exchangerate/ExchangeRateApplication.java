package com.exchangerate;

import com.exchangerate.client.ExchangeRateDataClient;
import com.exchangerate.client.MockExchangeRateDataClient;
import com.exchangerate.dao.ExchangeRateDao;
import com.exchangerate.dto.ExchangeRateFillerConfig;
import com.exchangerate.entity.ExchangeRate;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAspect;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.exchangerate.managed.ExchangeRateFillerJob;
import com.exchangerate.mapper.ExchangeRateMapper;
import com.exchangerate.resource.ExchangeRateResource;
import com.exchangerate.service.ExchangeRateServiceImpl;
import com.exchangerate.service.ExchangeRateService;
import org.hibernate.SessionFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExchangeRateApplication extends Application<ExchangeRateConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExchangeRateApplication().run(args);
    }

    @Override
    public void run(ExchangeRateConfiguration exchangeRateConfiguration, Environment environment) throws Exception {

        /*
        * Not using any dependency injection framework for dependencies.
        * */

        //register resources
        final ExchangeRateDataClient exchangeRateDataClient = new MockExchangeRateDataClient();
        final ExchangeRateDao exchangeRateDao = new ExchangeRateDao(hibernate.getSessionFactory());
        final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

        final ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl(exchangeRateDataClient, exchangeRateDao,
                exchangeRateMapper);
        environment.jersey().register(new ExchangeRateResource(exchangeRateService));

        //register com.exchangerate.managed services
        final UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory =
                new UnitOfWorkAwareProxyFactory("default", hibernate.getSessionFactory());
        final ExchangeRateFillerJob exchangeRateFillerJob = unitOfWorkAwareProxyFactory.create(
                ExchangeRateFillerJob.class, new Class[]{ExchangeRateService.class, ScheduledExecutorService.class, ExchangeRateFillerConfig.class},
                new Object[]{exchangeRateService, getScheduledExecutorService(), exchangeRateConfiguration.getExchangeRateFillerConfig()});
        environment.lifecycle().manage(exchangeRateFillerJob);

    }

    @Override
    public void initialize(Bootstrap<ExchangeRateConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new MigrationsBundle<ExchangeRateConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ExchangeRateConfiguration configuration) {
                return configuration.database;
            }
        });
    }

    private final HibernateBundle<ExchangeRateConfiguration> hibernate = new HibernateBundle<ExchangeRateConfiguration>(
            ExchangeRate.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ExchangeRateConfiguration configuration) {
            return configuration.database;
        }
    };

    private final ScheduledExecutorService getScheduledExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
}
