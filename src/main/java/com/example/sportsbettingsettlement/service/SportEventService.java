package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;

public interface SportEventService {

    void publish(SportEventOutcome sportEventOutcome);

    void handleSettlements(SportEventOutcome sportEventOutcome);
}