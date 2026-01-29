package com.example.sportsbettingsettlement.seed;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.sportsbettingsettlement.entity.BetEntity;
import com.example.sportsbettingsettlement.repository.BetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DataInitializerTest {

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
        dataInitializer.run();
        verify(betRepository, times(3)).save(any(BetEntity.class));
    }
}
