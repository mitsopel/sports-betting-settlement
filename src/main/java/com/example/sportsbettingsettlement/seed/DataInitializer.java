package com.example.sportsbettingsettlement.seed;

import com.example.sportsbettingsettlement.entity.BetEntity;
import com.example.sportsbettingsettlement.repository.BetRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

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

    private static final BigDecimal BET_AMOUNT_10 = BigDecimal.valueOf(10);
    private static final BigDecimal BET_AMOUNT_20 = BigDecimal.valueOf(20);
    private static final BigDecimal BET_AMOUNT_15 = BigDecimal.valueOf(15);

    private final BetRepository betRepository;

    @Override
    public void run(String... args) {
        betRepository.save(new BetEntity(BET_ID_1, USER_ID_101, EVENT_ID_1, MARKET_ID_1, WINNER_ID_1, BET_AMOUNT_10));
        betRepository.save(new BetEntity(BET_ID_2, USER_ID_102, EVENT_ID_1, MARKET_ID_1, WINNER_ID_2, BET_AMOUNT_20));
        betRepository.save(new BetEntity(BET_ID_3, USER_ID_103, EVENT_ID_2, MARKET_ID_2, WINNER_ID_3, BET_AMOUNT_15));
    }
}
