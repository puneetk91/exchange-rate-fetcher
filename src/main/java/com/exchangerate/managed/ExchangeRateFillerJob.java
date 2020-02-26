package com.exchangerate.managed;

import com.exchangerate.dto.ExchangeRateCurrencyFillerConfig;
import com.exchangerate.dto.ExchangeRateFillerConfig;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.lifecycle.Managed;
import org.apache.commons.collections4.CollectionUtils;
import com.exchangerate.service.ExchangeRateService;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static org.slf4j.LoggerFactory.getLogger;

//This job runs to refresh the latest exchange rates
public class ExchangeRateFillerJob implements Managed {

    private final ExchangeRateService exchangeRateService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final ExchangeRateFillerConfig config;
    private ExecutorService executorService;

    Logger logger = getLogger(ExchangeRateFillerJob.class);

    public ExchangeRateFillerJob(ExchangeRateService exchangeRateService,
                                 ScheduledExecutorService scheduledExecutorService,
                                 ExchangeRateFillerConfig config) {
        this.exchangeRateService = exchangeRateService;
        this.scheduledExecutorService = scheduledExecutorService;
        this.config = config;
    }

    @Override
    public void start() throws Exception {
        if (config.isEnabled()) {
            initializeExecutorService();
        }
    }

    @Override
    public void stop() throws Exception {
        if (config.isEnabled()) {
            scheduledExecutorService.shutdown();
            executorService.shutdown();
        }
    }

    public void initializeExecutorService() {
        int concurrency = config.getConcurrency();
        Runnable runnableTask = () -> {
            try {
                updateExchangeRates(config.getExchangeRateCurrencyFillerConfigList());
                logger.info("successfully ran the exchange rate updates");
            } catch (Exception e) {
                logger.error("exchange rate update failed {}", e);
            }
        };

        executorService = new ThreadPoolExecutor(concurrency, concurrency, 0L,
                config.getTimeUnit(),
                new ArrayBlockingQueue<>(concurrency),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        scheduledExecutorService.scheduleAtFixedRate(runnableTask, 0, config.getTimeInterval(),
                config.getTimeUnit());
    }

    @UnitOfWork
    public void updateExchangeRates(List<ExchangeRateCurrencyFillerConfig> configs) {
        if (CollectionUtils.isEmpty(configs))
            return;

        configs.stream().forEach(config -> {
            exchangeRateService.refreshLatestExchangeRates(config.getSourceCurrency(),
                    config.getTargetCurrency());
            logger.info("successfully refreshed the latest exchange rate");
        });
    }

}
