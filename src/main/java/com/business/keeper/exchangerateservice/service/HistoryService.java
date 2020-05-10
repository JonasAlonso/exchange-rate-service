package com.business.keeper.exchangerateservice.service;

import com.business.keeper.exchangerateservice.model.ExchangeRateResultDto;

import java.util.List;

public interface HistoryService {

     void  createHistoryEntry(ExchangeRateResultDto exchangeRateResultDto);

     List<ExchangeRateResultDto> getDailyHistory(Integer yyyy, Integer MM, Integer dd);

     List<ExchangeRateResultDto> getMonthlyHistory(Integer yyyy, Integer MM);
}
