package com.example.sportsbettingsettlement.controller;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bets")
@RequiredArgsConstructor
public class BetController {

    private final BetRepository betRepository;

    @GetMapping
    public List<Bet> findAll() {
        return betRepository.findAll();
    }
}
