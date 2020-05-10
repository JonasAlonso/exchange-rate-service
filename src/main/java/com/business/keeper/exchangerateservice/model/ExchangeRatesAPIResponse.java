package com.business.keeper.exchangerateservice.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class ExchangeRatesAPIResponse {

    private Map<Currency, Double> rates;
    private Currency base;
    private LocalDate date;
}
