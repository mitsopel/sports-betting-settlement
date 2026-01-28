package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.Bet;
import java.util.List;

public interface BetService {
    List<Bet> findAll();
}
