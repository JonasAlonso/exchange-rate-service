package com.business.keeper.exchangerateservice.mapper;

import com.business.keeper.exchangerateservice.entity.HistoryEntity;
import com.business.keeper.exchangerateservice.model.ExchangeRateResultDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HistoryMapper {

    public HistoryEntity fromDtoToEntity(ExchangeRateResultDto exchangeRateResultDto){
        return HistoryEntity.builder()
                .average(exchangeRateResultDto.getAverage())
                .base(exchangeRateResultDto.getBase())
                .exchangeRate(exchangeRateResultDto.getExchangeRate())
                .target(exchangeRateResultDto.getTarget())
                .trend(exchangeRateResultDto.getTrend())
                .creationDate(new Date())
                .build();
    }

    public ExchangeRateResultDto fromEntityToDto(HistoryEntity historyEntity){
        return ExchangeRateResultDto.builder()
                .average(historyEntity.getAverage())
                .base(historyEntity.getBase())
                .exchangeRate(historyEntity.getExchangeRate())
                .target(historyEntity.getTarget())
                .trend(historyEntity.getTrend())
                .build();
    }
}
