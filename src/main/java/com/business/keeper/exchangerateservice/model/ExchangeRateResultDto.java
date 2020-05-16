package com.business.keeper.exchangerateservice.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeRateResultDto {

    private Currency base;
    private Currency target;
    private Double exchangeRate;
    private BigDecimal average;
    private RateTrend trend;
    
}
