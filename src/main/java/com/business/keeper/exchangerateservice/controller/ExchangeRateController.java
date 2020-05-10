package com.business.keeper.exchangerateservice.controller;

import com.business.keeper.exchangerateservice.model.Currency;
import com.business.keeper.exchangerateservice.model.ExchangeRateResultDto;
import com.business.keeper.exchangerateservice.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/exchange-rate")
public class ExchangeRateController {

    private ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService){
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/{date}/{baseCurrency}/{targetCurrency}")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeRateResultDto getExchangeRateForGivenDateAndCurrencies(@PathVariable String date, @PathVariable Currency baseCurrency, @PathVariable Currency targetCurrency){
        log.info("Calculating exchange rate, average and trend for params: {},{},{}", date, baseCurrency, targetCurrency);
        return exchangeRateService.getExchangeRateForGivenDateAndCurrencies(date, baseCurrency,targetCurrency);
    }

}
