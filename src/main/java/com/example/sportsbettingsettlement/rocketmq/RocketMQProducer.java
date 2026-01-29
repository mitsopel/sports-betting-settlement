package com.example.sportsbettingsettlement.rocketmq;

import com.example.sportsbettingsettlement.domain.BetSettlementMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RocketMQProducer {

    @Value("${rocketmq.topics.bet-settlements}")
    private String betSettlementsTopic;

    public void send(BetSettlementMessage betSettlementMessage) {
        log.info("[MOCK ROCKETMQ] Sending to topic {}: {}", betSettlementsTopic, betSettlementMessage);
    }
}
