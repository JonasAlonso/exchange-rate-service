package com.business.keeper.exchangerateservice.service;

import com.business.keeper.exchangerateservice.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public interface ExchangeRateService {
    ExchangeRateResultDto getExchangeRateForGivenDateAndCurrencies(String date, Currency baseCurrency, Currency targetCurrency);

    BigDecimal calculateAverage(HistoryExchangeRateAPIResponse historyExchangeRateAPIResponse, Currency target);

    RateTrend determineRateTrend(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target);
}