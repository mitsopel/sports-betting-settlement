package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import com.example.sportsbettingsettlement.kafka.SportEventOutcomeKafkaProducer;
import com.example.sportsbettingsettlement.rocketmq.BetSettlementProducer;
import com.example.sportsbettingsettlement.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportEventOutcomeServiceImpl implements SportEventOutcomeService {

    private final SportEventOutcomeKafkaProducer sportEventOutcomeKafkaProducer;
    private final BetRepository betRepository;
    private final BetSettlementProducer betSettlementProducer;

    @Override
    public void publish(SportEventOutcomeDto sportEventOutcomeDto) {
        SportEventOutcome sportEventOutcome = new SportEventOutcome(
            sportEventOutcomeDto.eventId(),
            sportEventOutcomeDto.eventName(),
            sportEventOutcomeDto.eventWinnerId()
        );
        sportEventOutcomeKafkaProducer.publish(sportEventOutcome);
    }

    @Override
    public void handle(SportEventOutcome sportEventOutcome) {
        List<Bet> betsToSettle = betRepository.findByEventId(sportEventOutcome.eventId());
        betsToSettle.forEach(bet -> betSettlementProducer.send(bet, sportEventOutcome));
    }
}
