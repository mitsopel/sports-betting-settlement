package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.persistence.BetEntity;
import com.example.sportsbettingsettlement.repository.BetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

    private final BetRepository betRepository;
    private final BetMapper betMapper;

    @Override
    public List<Bet> findAll() {
        List<BetEntity> betEntities = betRepository.findAll();
        return betMapper.toDomainList(betEntities);
    }
}
