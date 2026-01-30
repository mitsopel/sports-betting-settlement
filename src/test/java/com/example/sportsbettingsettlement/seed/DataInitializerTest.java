package com.example.sportsbettingsettlement.seed;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.sportsbettingsettlement.entity.BetEntity;
import com.example.sportsbettingsettlement.repository.BetRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DataInitializerTest {

    private static final long BET_ID_1 = 1L;
    private static final long BET_ID_2 = 2L;
    private static final long BET_ID_3 = 3L;

    private static final long USER_ID_101 = 101L;
    private static final long USER_ID_102 = 102L;
    private static final long USER_ID_103 = 103L;

    private static final String EVENT_ID_1 = "EVT-1";
    private static final String EVENT_ID_2 = "EVT-2";

    private static final String MARKET_ID_1 = "MKT-1";
    private static final String MARKET_ID_2 = "MKT-2";

    private static final String WINNER_ID_1 = "WIN-1";
    private static final String WINNER_ID_2 = "WIN-2";
    private static final String WINNER_ID_3 = "WIN-3";

    private static final BigDecimal AMOUNT_10 = BigDecimal.valueOf(10);
    private static final BigDecimal AMOUNT_20 = BigDecimal.valueOf(20);
    private static final BigDecimal AMOUNT_15 = BigDecimal.valueOf(15);

    @Mock
    private BetRepository betRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSeedThreeBetsOnRun() throws Exception {
        ArgumentCaptor<BetEntity> captor = ArgumentCaptor.forClass(BetEntity.class);

        dataInitializer.run();

        verify(betRepository, times(3)).save(captor.capture());
        List<BetEntity> betEntities = captor.getAllValues();
        assertThat(betEntities).hasSize(3);
        assertThat(betEntities).extracting(BetEntity::getBetId)
            .containsExactlyInAnyOrder(BET_ID_1, BET_ID_2, BET_ID_3);

        assertThat(betEntities).filteredOn(betEntity -> betEntity.getBetId().equals(BET_ID_1)).first()
            .satisfies(betEntity -> {
                assertThat(betEntity.getUserId()).isEqualTo(USER_ID_101);
                assertThat(betEntity.getEventId()).isEqualTo(EVENT_ID_1);
                assertThat(betEntity.getEventMarketId()).isEqualTo(MARKET_ID_1);
                assertThat(betEntity.getEventWinnerId()).isEqualTo(WINNER_ID_1);
                assertThat(betEntity.getBetAmount()).isEqualByComparingTo(AMOUNT_10);
            });

        assertThat(betEntities).filteredOn(betEntity -> betEntity.getBetId().equals(BET_ID_2)).first()
            .satisfies(betEntity -> {
                assertThat(betEntity.getUserId()).isEqualTo(USER_ID_102);
                assertThat(betEntity.getEventId()).isEqualTo(EVENT_ID_1);
                assertThat(betEntity.getEventMarketId()).isEqualTo(MARKET_ID_1);
                assertThat(betEntity.getEventWinnerId()).isEqualTo(WINNER_ID_2);
                assertThat(betEntity.getBetAmount()).isEqualByComparingTo(AMOUNT_20);
            });

        assertThat(betEntities).filteredOn(betEntity -> betEntity.getBetId().equals(BET_ID_3)).first()
            .satisfies(betEntity -> {
                assertThat(betEntity.getUserId()).isEqualTo(USER_ID_103);
                assertThat(betEntity.getEventId()).isEqualTo(EVENT_ID_2);
                assertThat(betEntity.getEventMarketId()).isEqualTo(MARKET_ID_2);
                assertThat(betEntity.getEventWinnerId()).isEqualTo(WINNER_ID_3);
                assertThat(betEntity.getBetAmount()).isEqualByComparingTo(AMOUNT_15);
            });
    }
}
