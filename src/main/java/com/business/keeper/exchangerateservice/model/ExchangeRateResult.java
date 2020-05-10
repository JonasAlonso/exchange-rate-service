package com.business.keeper.exchangerateservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateResult {

    private Currency base;
    private Currency target;
    private Double exchangeRate;
    private Double average;
    private RateTrend trend;
    
}
