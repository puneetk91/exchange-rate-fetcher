import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import dto.ExchangeRateFillerConfig;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ExchangeRateConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDatabase() {
        return database;
    }

    @NotNull
    @JsonProperty("exchangeRateFillerConfig")
    public ExchangeRateFillerConfig exchangeRateFillerConfig;

    public ExchangeRateFillerConfig getExchangeRateFillerConfig() {
        return exchangeRateFillerConfig;
    }
}
