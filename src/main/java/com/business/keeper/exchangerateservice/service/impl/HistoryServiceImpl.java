package com.business.keeper.exchangerateservice.service.impl;

import com.business.keeper.exchangerateservice.entity.HistoryEntity;
import com.business.keeper.exchangerateservice.mapper.HistoryMapper;
import com.business.keeper.exchangerateservice.model.ExchangeRateResultDto;
import com.business.keeper.exchangerateservice.repository.HistoryRepository;
import com.business.keeper.exchangerateservice.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

    private HistoryRepository historyRepository;
    private HistoryMapper historyMapper;

    public HistoryServiceImpl(HistoryRepository historyRepository, HistoryMapper historyMapper){
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    @Override
    @Transactional
    public void createHistoryEntry(ExchangeRateResultDto exchangeRateResultDto) {
        HistoryEntity historyEntity = historyMapper.fromDtoToEntity(exchangeRateResultDto);

        HistoryEntity entity = historyRepository.save(historyEntity);
        log.info("Persisted entity: {}", entity);
    }

    @Override
    @Transactional
    public List<ExchangeRateResultDto> getDailyHistory(Integer yyyy, Integer MM, Integer dd) {
        Date date = parseDate(yyyy + "-" + MM + "-" + dd);
        List<HistoryEntity> res = historyRepository.findAllByCreationDate(date);

        log.info("Daily history for date: {}/{}/{} retrieved.", yyyy, MM, dd);
        return createHistoryListDto(res);
    }

    @Override
    public List<ExchangeRateResultDto> getMonthlyHistory(Integer yyyy, Integer MM) {
        Date dateFrom = parseDate(yyyy + "-" + MM + "-" + "01");
        Date dateUntil = parseDate(yyyy + "-" + MM + "-" + determineLastDayOfMonth(MM.toString()));

        List<HistoryEntity> res = historyRepository.findAllByCreationDateBetween(dateFrom,dateUntil);

        log.info("Monthly history for: {}/{} retrieved.", yyyy, MM);
        return createHistoryListDto(res);
    }

    private List<ExchangeRateResultDto> createHistoryListDto(List<HistoryEntity> historyEntityList){
        return historyEntityList.stream().map(historyMapper::fromEntityToDto).collect(Collectors.toList());
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private static String determineLastDayOfMonth(String month){
            String result;
            switch (month){
                case "02": result="28"; break;
                case "04": case   "06": case "09": case"11": result="30"; break;
                default: result="31";
            }

            return  result;
    }

}
