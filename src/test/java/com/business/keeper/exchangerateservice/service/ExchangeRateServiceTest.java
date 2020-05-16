package com.business.keeper.exchangerateservice.service;

import com.business.keeper.exchangerateservice.facade.ExchangeRatesClient;
import com.business.keeper.exchangerateservice.model.Currency;
import com.business.keeper.exchangerateservice.model.RateTrend;
import com.business.keeper.exchangerateservice.service.impl.ExchangeRateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRatesClient exchangeRatesClient;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl(exchangeRatesClient, historyService);

    @Test
    public void isAssendingRateTrendTest(){
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2017,06,11), Map.of(Currency.USD,BigDecimal.valueOf(1.1)));
        rates.put(LocalDate.of(2017,06,12), Map.of(Currency.USD,BigDecimal.valueOf(1.2)));
        rates.put(LocalDate.of(2017,06,13), Map.of(Currency.USD,BigDecimal.valueOf(1.3)));
        rates.put(LocalDate.of(2017,06,14), Map.of(Currency.USD,BigDecimal.valueOf(1.4)));
        rates.put(LocalDate.of(2017,06,15), Map.of(Currency.USD,BigDecimal.valueOf(1.5)));

        log.info("Rates: {}", rates);

        assertEquals(RateTrend.ASCENDING, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }

    @Test
    public void isDescendingRateTrendTest(){
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2017,06,11), Map.of(Currency.USD,BigDecimal.valueOf(1.5)));
        rates.put(LocalDate.of(2017,06,12), Map.of(Currency.USD,BigDecimal.valueOf(1.4)));
        rates.put(LocalDate.of(2017,06,13), Map.of(Currency.USD,BigDecimal.valueOf(1.3)));
        rates.put(LocalDate.of(2017,06,14), Map.of(Currency.USD,BigDecimal.valueOf(1.2)));
        rates.put(LocalDate.of(2017,06,15), Map.of(Currency.USD,BigDecimal.valueOf(1.1)));

        assertEquals(RateTrend.DESCENDING, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }

    @Test
    public void isConstantRateTrendTest(){
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2017,06,11), Map.of(Currency.USD,BigDecimal.valueOf(1.11)));
        rates.put(LocalDate.of(2017,06,12), Map.of(Currency.USD,BigDecimal.valueOf(1.11)));
        rates.put(LocalDate.of(2017,06,13), Map.of(Currency.USD,BigDecimal.valueOf(1.11)));
        rates.put(LocalDate.of(2017,06,14), Map.of(Currency.USD,BigDecimal.valueOf(1.11)));
        rates.put(LocalDate.of(2017,06,15), Map.of(Currency.USD,BigDecimal.valueOf(1.11)));

        assertEquals(RateTrend.CONSTANT, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }

    @Test
    public void isUndefinedRateTrendTest(){
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = new TreeMap<>();

        rates.put(LocalDate.of(2017,06,11), Map.of(Currency.USD,BigDecimal.valueOf(1.1)));
        rates.put(LocalDate.of(2017,06,12), Map.of(Currency.USD,BigDecimal.valueOf(1.4)));
        rates.put(LocalDate.of(2017,06,13), Map.of(Currency.USD,BigDecimal.valueOf(1.1)));
        rates.put(LocalDate.of(2017,06,14), Map.of(Currency.USD,BigDecimal.valueOf(1.2)));
        rates.put(LocalDate.of(2017,06,15), Map.of(Currency.USD,BigDecimal.valueOf(1.1)));

        assertEquals(RateTrend.UNDEFINED, exchangeRateService.determineRateTrend(rates, Currency.USD));
    }
}