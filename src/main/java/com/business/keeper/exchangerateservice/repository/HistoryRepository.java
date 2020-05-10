package com.business.keeper.exchangerateservice.repository;

import com.business.keeper.exchangerateservice.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

    List<HistoryEntity> findAllByCreationDate(Date creationDate);

    List<HistoryEntity> findAllByCreationDateBetween(Date dateFrom, Date dateUntil);
}
