package com.example.sportsbettingsettlement.service;

import com.example.sportsbettingsettlement.persistence.BetEntity;
import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.kafka.KafkaProducer;
import com.example.sportsbettingsettlement.rocketmq.BetSettlementMessage;
import com.example.sportsbettingsettlement.rocketmq.RocketMQProducer;
import com.example.sportsbettingsettlement.repository.BetRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportEventOutcomeServiceImpl implements SportEventOutcomeService {

    private final KafkaProducer kafkaProducer;
    private final BetRepository betRepository;
    private final RocketMQProducer rocketMQProducer;

    @Override
    public void publish(SportEventOutcome sportEventOutcome) {
        kafkaProducer.publish(sportEventOutcome);
    }

    @Override
    public void handle(SportEventOutcome sportEventOutcome) {
        List<BetEntity> betsToSettle = betRepository.findByEventId(sportEventOutcome.getEventId());
        betsToSettle.stream()
            // Build settlement messages ONLY for winners and forward to RocketMQ producer
            .filter(bet -> bet.getEventWinnerId().equals(sportEventOutcome.getEventWinnerId()))
            .map(bet -> new BetSettlementMessage(
                bet.getBetId(),
                bet.getUserId(),
                bet.getEventId(),
                bet.getEventMarketId(),
                bet.getEventWinnerId(), // predicted winner
                bet.getBetAmount(),
                sportEventOutcome.getEventWinnerId(), // actual winner
                bet.getBetAmount().multiply(BigDecimal.valueOf(2)) // payout example that duplicates the bet amount
            ))
            .forEach(rocketMQProducer::send);
    }
}
