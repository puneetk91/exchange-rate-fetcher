package com.exchangerate.resource;

import com.exchangerate.dto.CurrencyCode;
import com.exchangerate.dto.ExchangeRate;
import com.exchangerate.service.ExchangeRateService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Path("/v0.1/exchange-rate")
@Produces(MediaType.APPLICATION_JSON)
public class ExchangeRateResource {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateResource(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GET
    @Path("/source/bitcoin/target/{target_currency}")
    @UnitOfWork
    public Response getExchangeRates(@PathParam("target_currency") @DefaultValue("USD") CurrencyCode targetCurrency,
                                     @QueryParam("from_time")Long from,
                                     @QueryParam("to_time")Long to,
                                     @QueryParam("latest") Boolean latest) {

        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        //source `bitcoin` can be parameterised here
        CurrencyCode sourceCurrency = CurrencyCode.BITCOIN;
        if (TRUE.equals(latest)) {
            exchangeRateList.add(exchangeRateService.getLatestExchangeRate(sourceCurrency, targetCurrency));
        } else {
            if (from == null || to == null || to.compareTo(from) < 0)
                throw new BadRequestException("Both from_time and to_time values should be non null for historical data!");
            exchangeRateList.addAll(exchangeRateService.getHistoricalData(new Timestamp(from), new Timestamp(to), sourceCurrency, targetCurrency));
        }
        return Response.ok().entity(exchangeRateList).build();
    }

    //Backfill can be further optimised to run in batches
    @POST
    @Path("/source/bitcoin/target/{target_currency}/backfill")
    @UnitOfWork
    public Response backfillExchangeRates(@PathParam("target_currency") @DefaultValue("USD") CurrencyCode targetCurrency,
                                     @QueryParam("from_time") Long from,
                                     @QueryParam("to_time") Long to) {

        //source `bitcoin` can be parameterised here
        CurrencyCode sourceCurrency = CurrencyCode.BITCOIN;
        if (from == null || to == null || to.compareTo(from) < 0)
            throw new BadRequestException("Both from_time and to_time values should be non null for historical data!");

        exchangeRateService.backfillExchangeRates(sourceCurrency, targetCurrency, new Timestamp(from), new Timestamp(to));

        return Response.ok().build();
    }

}
