package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import java.util.List;

public interface BetService {

    List<Bet> findAll();

    void handleSettlements(SportEventOutcome sportEventOutcome);
}
