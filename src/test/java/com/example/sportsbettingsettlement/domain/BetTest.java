package com.example.sportsbettingsettlement.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BetTest {

    private static final long BET_ID = 1L;
    private static final long USER_ID_101 = 101L;
    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final String WINNER_ID_WIN_2 = "WIN-2";
    private static final BigDecimal STAKE_TEN = BigDecimal.TEN;

    @Test
    void shouldReturnTrueForHasWonWhenMatches() {
        Bet bet = new Bet(BET_ID, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, STAKE_TEN);
        assertThat(bet.hasWon(WINNER_ID_WIN_1)).isTrue();
    }

    @Test
    void shouldReturnFalseForHasWonWhenWinnerDiffers() {
        Bet bet = new Bet(BET_ID, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, STAKE_TEN);
        assertThat(bet.hasWon(WINNER_ID_WIN_2)).isFalse();
    }

    @Test
    void shouldReturnFalseForHasWonWhenActualIsNull() {
        Bet bet = new Bet(BET_ID, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, STAKE_TEN);
        assertThat(bet.hasWon(null)).isFalse();
    }
}
