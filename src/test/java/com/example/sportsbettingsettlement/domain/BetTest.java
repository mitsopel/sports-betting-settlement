package com.example.sportsbettingsettlement.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BetTest {

    @Test
    void shouldReturnTrueForHasWonWhenMatches() {
        Bet bet = new Bet(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        assertThat(bet.hasWon("WIN-1")).isTrue();
    }

    @Test
    void shouldReturnFalseForHasWonWhenWinnerDiffers() {
        Bet bet = new Bet(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        assertThat(bet.hasWon("WIN-2")).isFalse();
    }

    @Test
    void shouldReturnFalseForHasWonWhenActualIsNull() {
        Bet bet = new Bet(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        assertThat(bet.hasWon(null)).isFalse();
    }
}
