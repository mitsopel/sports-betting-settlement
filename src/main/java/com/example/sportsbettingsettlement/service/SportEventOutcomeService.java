package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;

public interface SportEventOutcomeService {
    void publish(SportEventOutcome sportEventOutcome);
    void handle(SportEventOutcome sportEventOutcome);
}