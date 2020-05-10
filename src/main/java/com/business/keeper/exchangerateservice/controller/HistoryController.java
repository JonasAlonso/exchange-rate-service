package com.business.keeper.exchangerateservice.controller;

import com.business.keeper.exchangerateservice.model.ExchangeRateResultDto;
import com.business.keeper.exchangerateservice.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/exchange-rate/history")
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }

    @GetMapping("/daily/{yyyy}/{MM}/{dd}")
    public List<ExchangeRateResultDto> getDailyHistory(@PathVariable Integer yyyy, @PathVariable Integer MM, @PathVariable Integer dd){
        log.info("Retriving historical daily information for params  (yyyy/MM/dd): {}/{}/{}");
        return historyService.getDailyHistory(yyyy, MM, dd);
    }

    @GetMapping("/monthly/{yyyy}/{MM}")
    public List<ExchangeRateResultDto> getMonthlyHistory(@PathVariable Integer yyyy, @PathVariable Integer MM){
        log.info("Retriving historical monthly information for params  (yyyy/MM/dd): {}/{}/{}");
        return historyService.getMonthlyHistory(yyyy, MM);
    }
}
