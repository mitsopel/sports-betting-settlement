package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;

public interface SportEventService {

    void add(SportEventOutcome sportEventOutcome);

    void handleBetSettlements(SportEventOutcome sportEventOutcome);
}