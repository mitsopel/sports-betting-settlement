package com.example.sportsbettingsettlement.controller;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.dto.BetDto;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.service.BetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bets")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;
    private final BetMapper betMapper;

    @GetMapping
    public List<BetDto> findAll() {
        List<Bet> bets = betService.findAll();
        return betMapper.toDtoList(bets);
    }
}
