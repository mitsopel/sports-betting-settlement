package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;

public interface SportEventOutcomeService {
    void publish(SportEventOutcomeDto sportEventOutcomeDto);
    void handle(SportEventOutcome sportEventOutcome);
}