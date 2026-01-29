package com.example.sportsbettingsettlement.rocketmq;

import static java.util.Objects.nonNull;

import com.example.sportsbettingsettlement.domain.BetSettlementMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class RocketMQProducer {

    private final ObjectProvider<RocketMQTemplate> rocketMQTemplateProvider;

    @Value("${rocketmq.topics.bet-settlements}")
    private String betSettlementsTopic;

    public void send(BetSettlementMessage betSettlementMessage) {
        RocketMQTemplate rocketMQTemplate = nonNull(rocketMQTemplateProvider)
            ? rocketMQTemplateProvider.getIfAvailable()
            : null;

        if (nonNull(rocketMQTemplate)) {
            try {
                rocketMQTemplate.convertAndSend(betSettlementsTopic, betSettlementMessage);

                log.info("[ROCKETMQ] Sent to topic {}: {}", betSettlementsTopic, betSettlementMessage);
                log.info("[ROCKETMQ] User with id {} has won and the payout is: {} EUR.",
                    betSettlementMessage.getUserId(), betSettlementMessage.getPayoutAmount());
            } catch (Exception ex) {
                log.error("[ROCKETMQ] Failed to send to topic {}. Falling back to log-only. Message: {}",
                    betSettlementsTopic, betSettlementMessage, ex);
                logMock(betSettlementMessage);
            }
        } else {
            logMock(betSettlementMessage);
        }
    }

    private void logMock(BetSettlementMessage betSettlementMessage) {
        log.info("[MOCK ROCKETMQ] Sending to topic {}: {}", betSettlementsTopic, betSettlementMessage);
        log.info("[MOCK ROCKETMQ] User with id {} has won and the payout is: {} EUR.",
            betSettlementMessage.getUserId(), betSettlementMessage.getPayoutAmount());
    }
}
