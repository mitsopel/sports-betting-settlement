package com.example.sportsbettingsettlement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.sportsbettingsettlement.repository.BetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SportsBettingSettlementApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BetRepository betRepository;

    @Test
    void shouldLoadContext() {
        // Smoke test to ensure Spring context and core beans load
        assertNotNull(applicationContext, "ApplicationContext should be initialized");
        assertNotNull(betRepository, "BetRepository bean should be available in context");
    }
}
