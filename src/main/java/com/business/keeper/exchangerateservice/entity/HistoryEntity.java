package com.business.keeper.exchangerateservice.entity;

import com.business.keeper.exchangerateservice.model.Currency;
import com.business.keeper.exchangerateservice.model.RateTrend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    private Currency base;
    private Currency target;
    private Double exchangeRate;
    private Double average;
    private RateTrend trend;
}
