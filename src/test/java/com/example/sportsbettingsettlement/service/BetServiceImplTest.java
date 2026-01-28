package com.example.sportsbettingsettlement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.persistence.BetEntity;
import com.example.sportsbettingsettlement.repository.BetRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class BetServiceImplTest {

    @Mock
    private BetRepository betRepository;

    @Spy
    private BetMapper betMapper = Mappers.getMapper(BetMapper.class);

    @InjectMocks
    private BetServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnMappedDomainObjectsOnFindAll() {
        var e1 = new BetEntity(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN);
        var e2 = new BetEntity(2L, 102L, "EVT-2", "MKT-2", "WIN-2", BigDecimal.ONE);
        when(betRepository.findAll()).thenReturn(List.of(e1, e2));

        List<Bet> result = service.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getBetId()).isEqualTo(1L);
        assertThat(result.get(1).getEventId()).isEqualTo("EVT-2");
    }
}
