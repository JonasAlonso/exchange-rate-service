package com.business.keeper.exchangerateservice.facade;

import com.business.keeper.exchangerateservice.model.Currency;
import com.business.keeper.exchangerateservice.model.ExchangeRatesAPIResponse;
import com.business.keeper.exchangerateservice.model.HistoryExchangeRateAPIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-rates-api", url = "${exchange.rates.api.url}")
public interface ExchangeRatesClient {

    //https://api.exchangeratesapi.io/2017-05-11?base=USD&symbols=EUR
    @GetMapping(value = "/{date}?base={base}&symbols={target}")
    ResponseEntity<ExchangeRatesAPIResponse> getExchangeRateForADate(
            @PathVariable String date,
            @PathVariable Currency base,
            @PathVariable Currency target
            );

    // https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-01-07&base=USD&symbols=EUR
    @GetMapping(value = "/history?start_at={start_at}&end_at={end_at}&base={base}&symbols={target}")
    ResponseEntity<HistoryExchangeRateAPIResponse> getHistoricalExchangeRate(
            @PathVariable String start_at,
            @PathVariable String end_at,
            @PathVariable Currency base,
            @PathVariable Currency target
            );
}
