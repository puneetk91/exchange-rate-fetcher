package resource;

import dto.CurrencyCode;
import dto.ExchangeRate;
import service.ExchangeRateService;

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
    public Response getExchangeRates(@QueryParam("currency_code") @DefaultValue("USD") CurrencyCode currencyCode,
                                     @QueryParam("from_time")Timestamp from,
                                     @QueryParam("to_time")Timestamp to,
                                     @QueryParam("latest") Boolean latest) {

        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        if (TRUE.equals(latest)) {
            exchangeRateList.add(exchangeRateService.getLatestExchangeRate(currencyCode, currencyCode));
        } else {
            if (from == null || to == null)
                throw new BadRequestException("Both from_time and to_time values should be non null for historical data!");
            exchangeRateList.addAll(exchangeRateService.getHistoricalData(from, to, currencyCode));
        }
        return Response.ok().entity(exchangeRateList).build();
    }

}
