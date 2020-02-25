import client.ExchangeRateDataClient;
import client.MockExchangeRateDataClient;
import dao.ExchangeRateDao;
import dto.ExchangeRateFillerConfig;
import entity.ExchangeRate;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import managed.ExchangeRateFillerJob;
import mapper.ExchangeRateMapper;
import resource.ExchangeRateResource;
import service.ExchangeRateServiceImpl;
import service.ExchangeRateService;

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

        //register managed services
        final ExchangeRateFillerJob exchangeRateFillerJob = new ExchangeRateFillerJob(exchangeRateService,
                getScheduledExecutorService(), exchangeRateConfiguration.getExchangeRateFillerConfig());
        environment.lifecycle().manage(exchangeRateFillerJob);

    }

    @Override
    public void initialize(Bootstrap<ExchangeRateConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new MigrationsBundle<ExchangeRateConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ExchangeRateConfiguration configuration) {
                return configuration.getDatabase();
            }
        });
    }

    private final HibernateBundle<ExchangeRateConfiguration> hibernate = new HibernateBundle<ExchangeRateConfiguration>(
            ExchangeRate.class) {

        @Override
        public DataSourceFactory getDataSourceFactory(ExchangeRateConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    private final ScheduledExecutorService getScheduledExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
}
