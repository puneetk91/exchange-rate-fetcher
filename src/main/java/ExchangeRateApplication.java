import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExchangeRateApplication extends Application<ExchangeRateConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExchangeRateApplication().run(args);
    }

    @Override
    public void run(ExchangeRateConfiguration exchangeRateConfiguration, Environment environment) throws Exception {

    }

    @Override
    public void initialize(Bootstrap<ExchangeRateConfiguration> bootstrap) {

    }
}
