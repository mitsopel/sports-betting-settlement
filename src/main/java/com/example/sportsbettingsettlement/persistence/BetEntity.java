package com.example.sportsbettingsettlement.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "bets",
    indexes = {
        @Index(name = "idx_bets_event_id", columnList = "event_id"),
        @Index(name = "idx_bets_user_id", columnList = "user_id")
    }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "betId")
@ToString
public class BetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bet_id", nullable = false)
    private Long betId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "event_id", nullable = false, length = 64)
    private String eventId;

    @Column(name = "event_market_id", nullable = false, length = 64)
    private String eventMarketId;

    @Column(name = "event_winner_id", nullable = false, length = 64)
    private String eventWinnerId;

    @Column(name = "bet_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal betAmount;
}
