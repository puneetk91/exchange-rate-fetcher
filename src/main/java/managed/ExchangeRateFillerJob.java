package managed;

import dto.ExchangeRateCurrencyFillerConfig;
import dto.ExchangeRateFillerConfig;
import io.dropwizard.lifecycle.Managed;
import org.apache.commons.collections4.CollectionUtils;
import service.ExchangeRateService;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ExchangeRateFillerJob implements Managed {

    private final ExchangeRateService exchangeRateService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final ExchangeRateFillerConfig config;
    private ExecutorService executorService;

    public ExchangeRateFillerJob(ExchangeRateService exchangeRateService,
                                 ScheduledExecutorService scheduledExecutorService,
                                 ExchangeRateFillerConfig config) {
        this.exchangeRateService = exchangeRateService;
        this.scheduledExecutorService = scheduledExecutorService;
        this.config = config;
    }

    private void run() {

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

    private void initializeExecutorService() {
        int concurrency = config.getConcurrency();
        Runnable runnableTask = () -> {
            try {
                updateExchangeRates(config.getExchangeRateCurrencyFillerConfigList());
            } catch (Exception e) {
                e.printStackTrace();
                //log instead
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

    private void updateExchangeRates(List<ExchangeRateCurrencyFillerConfig> configs) {
        if (CollectionUtils.isEmpty(configs))
            return;

        configs.stream().forEach(config -> {
            exchangeRateService.refreshLatestExchangeRates(config.getSourceCurrency(),
                    config.getTargetCurrency());
            //Backfill till configured or current date, if opted for
            if (config.isBackfillEnabled()) {
                exchangeRateService.backfillExchangeRates(config.getSourceCurrency(), config.getTargetCurrency(),
                        config.getBackfillFrom(), config.getBackfillTill() == null ? new Date() : config.getBackfillTill());
            }
        });
    }

}
