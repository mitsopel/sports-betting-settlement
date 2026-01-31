package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.domain.BetSettlementMessage;
import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.entity.BetEntity;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.repository.BetRepository;
import com.example.sportsbettingsettlement.rocketmq.RocketMQProducer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

    private final BetRepository betRepository;
    private final RocketMQProducer rocketMQProducer;
    private final BetMapper betMapper;

    @Override
    public List<Bet> findAll() {
        List<BetEntity> betEntities = betRepository.findAll();
        return betMapper.toDomainList(betEntities);
    }

    @Override
    public void handleSettlements(SportEventOutcome sportEventOutcome) {
        List<BetEntity> betEntityList = betRepository.findByEventId(sportEventOutcome.getEventId());
        List<Bet> betDomainList = betMapper.toDomainList(betEntityList);

        // Send bet settlement messages ONLY to winners through RocketMQ producer
        betDomainList.stream()
            .filter(bet -> bet.hasWon(sportEventOutcome.getEventWinnerId()))
            .map(BetSettlementMessage::createBetSettlementMessage)
            .forEach(rocketMQProducer::send);
    }
}
