package com.business.keeper.exchangerateservice.service.impl;

import com.business.keeper.exchangerateservice.facade.ExchangeRatesClient;
import com.business.keeper.exchangerateservice.model.*;
import com.business.keeper.exchangerateservice.service.ExchangeRateService;
import com.business.keeper.exchangerateservice.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private ExchangeRatesClient exchangeRatesClient;
    private HistoryService historyService;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRatesClient exchangeRatesClient, HistoryService historyService){
        this.exchangeRatesClient = exchangeRatesClient;
        this.historyService = historyService;
    }

    @Override
    public ExchangeRateResultDto getExchangeRateForGivenDateAndCurrencies(String date, Currency baseCurrency, Currency targetCurrency) {
        LocalDate startDate = LocalDate.parse(date).minusDays(5);
        ResponseEntity<HistoryExchangeRateAPIResponse> historical = exchangeRatesClient.getHistoricalExchangeRate(startDate.toString(), date, baseCurrency, targetCurrency);
        ResponseEntity<ExchangeRatesAPIResponse> res = exchangeRatesClient.getExchangeRateForADate(date, baseCurrency, targetCurrency);

        ExchangeRateResultDto result = ExchangeRateResultDto.builder().base(baseCurrency)
                .exchangeRate(res.getBody().getRates().get(targetCurrency))
                .target(targetCurrency)
                .average(calculateAverage(historical.getBody(), targetCurrency))
                .trend(determineRateTrend(historical.getBody().getRates(), targetCurrency))
                .build();
        historyService.createHistoryEntry(result);
        return result;
    }

    @Override
    public BigDecimal calculateAverage(HistoryExchangeRateAPIResponse historyExchangeRateAPIResponse, Currency target){
        TreeMap<LocalDate, Map<Currency, BigDecimal>> rates = historyExchangeRateAPIResponse.getRates();
        LocalDate end = historyExchangeRateAPIResponse.getEnd_at();
        LocalDate start = historyExchangeRateAPIResponse.getStart_at();
        Currency base = historyExchangeRateAPIResponse.getBase();

        BigDecimal sum = BigDecimal.valueOf(0.0);
        int count = 0;

        log.info("Calculating the average rate between {} and {} for currencies: {},{}",start,end,base, target);
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)){
            if (rates.containsKey(date)){
                count++;
                sum = sum.add(rates.get(date).get(target));
                //sum = sum + rates.get(date).get(target);
            }
        }


        log.info("Calculated sum: {}. Calculated average: {}", sum, (count==0)? 0: sum.divide(BigDecimal.valueOf(count)));

        return ((count==0) ? BigDecimal.valueOf(0.0) : sum.divide(BigDecimal.valueOf(count)));
    }

    @Override
    public RateTrend determineRateTrend(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target) {
        if (isAscending(rates,target)){
            return RateTrend.ASCENDING;
        } else if (isDescending(rates,target)){
            return RateTrend.DESCENDING;
        } else if (isConstant(rates,target)){
            return  RateTrend.CONSTANT;
        }
        return RateTrend.UNDEFINED;
    }

    private boolean isAscending(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target){
        boolean isAscending = true;
        Iterator<Map.Entry<LocalDate, Map<Currency, BigDecimal>>> entries = rates.entrySet().iterator();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> entry = entries.next();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> next;

        while(isAscending && entries.hasNext()){
            next = entries.next();

            log.info("Calculating trend between {} and {}. ",entry.getValue(), next.getValue());
            if (entry.getValue().get(target).compareTo(next.getValue().get(target))>=0){
                isAscending = false;
            }
            entry = next;
        }
        log.info("The trend is{} ascending.", ((isAscending) ? "" : " not"));
        return isAscending;
    }

    private boolean isDescending(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target){
        boolean isDescending = true;
        Iterator<Map.Entry<LocalDate, Map<Currency, BigDecimal>>> entries = rates.entrySet().iterator();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> entry = entries.next();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> next;

        while(isDescending && entries.hasNext()){
            next = entries.next();
            if (entry.getValue().get(target).compareTo(next.getValue().get(target))<=0){
                isDescending = false;
            }
            entry = next;
        }
        log.info("The trend is{} descending.", ((isDescending) ? "" : " not"));

        return isDescending;
    }

    private boolean isConstant(TreeMap<LocalDate, Map<Currency, BigDecimal>> rates, Currency target){
        boolean isConstant = true;
        Iterator<Map.Entry<LocalDate, Map<Currency, BigDecimal>>> entries = rates.entrySet().iterator();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> entry = entries.next();
        Map.Entry<LocalDate, Map<Currency, BigDecimal>> next;

        while(isConstant && entries.hasNext()){
            next = entries.next();
            if (entry.getValue().get(target).compareTo(next.getValue().get(target))!=0){
                isConstant = false;
            }
            entry = next;
        }
        log.info("The trend is{} constant.", ((isConstant) ? "" : " not"));

        return isConstant;
    }
}
