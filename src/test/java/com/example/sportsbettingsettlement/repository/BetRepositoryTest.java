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

    @Autowired
    private BetRepository betRepository;

    @Test
    void shouldFindByEventIdAndReturnOnlyMatchingBets() {
        betRepository.save(new BetEntity(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN));
        betRepository.save(new BetEntity(2L, 102L, "EVT-1", "MKT-1", "WIN-2", BigDecimal.ONE));
        betRepository.save(new BetEntity(3L, 103L, "EVT-2", "MKT-2", "WIN-3", BigDecimal.valueOf(5)));

        List<BetEntity> betEntities = betRepository.findByEventId("EVT-1");
        assertThat(betEntities).extracting(BetEntity::getBetId).containsExactlyInAnyOrder(1L, 2L);
    }
}
