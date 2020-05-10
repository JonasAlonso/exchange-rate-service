package com.business.keeper.exchangerateservice.service;

import com.business.keeper.exchangerateservice.model.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public interface ExchangeRateService {
    ExchangeRateResultDto getExchangeRateForGivenDateAndCurrencies(String date, Currency baseCurrency, Currency targetCurrency);

    Double calculateAverage(HistoryExchangeRateAPIResponse historyExchangeRateAPIResponse, Currency target);

    RateTrend determineRateTrend(TreeMap<LocalDate, Map<Currency, Double>> rates, Currency target);
}