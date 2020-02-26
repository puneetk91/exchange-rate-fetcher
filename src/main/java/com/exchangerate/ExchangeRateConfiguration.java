package com.exchangerate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.exchangerate.dto.ExchangeRateFillerConfig;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ExchangeRateConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    public DataSourceFactory database = new DataSourceFactory();
//
//    @JsonProperty("swagger")
//    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @NotNull
    @JsonProperty("exchangeRateFillerConfig")
    public ExchangeRateFillerConfig exchangeRateFillerConfig;

    public ExchangeRateFillerConfig getExchangeRateFillerConfig() {
        return exchangeRateFillerConfig;
    }
}
