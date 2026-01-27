package com.example.sportsbettingsettlement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bet {

    @Id
    private Long betId;
    private Long userId;
    private String eventId;
    private String eventMarketId;
    private String eventWinnerId;
    private BigDecimal betAmount;
}
