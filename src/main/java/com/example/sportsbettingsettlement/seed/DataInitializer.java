package com.example.sportsbettingsettlement.seed;

import com.example.sportsbettingsettlement.persistence.BetEntity;
import com.example.sportsbettingsettlement.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BetRepository betRepository;

    @Override
    public void run(String... args) {
        betRepository.save(new BetEntity(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.valueOf(10)));
        betRepository.save(new BetEntity(2L, 102L, "EVT-1", "MKT-1", "WIN-2", BigDecimal.valueOf(20)));
        betRepository.save(new BetEntity(3L, 103L, "EVT-2", "MKT-2", "WIN-3", BigDecimal.valueOf(15)));
    }
}
