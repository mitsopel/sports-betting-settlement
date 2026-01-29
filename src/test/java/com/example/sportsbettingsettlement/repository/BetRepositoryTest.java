package com.example.sportsbettingsettlement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sportsbettingsettlement.entity.BetEntity;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BetRepositoryTest {

    private static final long BET_ID_1 = 1L;
    private static final long BET_ID_2 = 2L;
    private static final long BET_ID_3 = 3L;
    private static final long USER_ID_101 = 101L;
    private static final long USER_ID_102 = 102L;
    private static final long USER_ID_103 = 103L;
    private static final String EVENT_ID_EVT_1 = "EVT-1";
    private static final String EVENT_ID_EVT_2 = "EVT-2";
    private static final String MARKET_ID_MKT_1 = "MKT-1";
    private static final String MARKET_ID_MKT_2 = "MKT-2";
    private static final String WINNER_ID_WIN_1 = "WIN-1";
    private static final String WINNER_ID_WIN_2 = "WIN-2";
    private static final String WINNER_ID_WIN_3 = "WIN-3";
    private static final String NON_EXISTENT_EVENT_ID = "NON-EXISTENT";

    @Autowired
    private BetRepository betRepository;

    @Test
    void shouldFindByEventIdAndReturnOnlyMatchingBets() {
        betRepository.save(
            new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_1, BigDecimal.TEN));
        betRepository.save(
            new BetEntity(BET_ID_2, USER_ID_102, EVENT_ID_EVT_1, MARKET_ID_MKT_1, WINNER_ID_WIN_2, BigDecimal.ONE));
        betRepository.save(new BetEntity(BET_ID_3, USER_ID_103, EVENT_ID_EVT_2, MARKET_ID_MKT_2, WINNER_ID_WIN_3,
            BigDecimal.valueOf(5)));

        List<BetEntity> betEntities = betRepository.findByEventId(EVENT_ID_EVT_1);
        assertThat(betEntities).extracting(BetEntity::getBetId).containsExactlyInAnyOrder(BET_ID_1, BET_ID_2);
    }

    @Test
    void shouldReturnEmptyListWhenEventIdNotFound() {
        List<BetEntity> betEntities = betRepository.findByEventId(NON_EXISTENT_EVENT_ID);
        assertThat(betEntities).isEmpty();
    }
}
