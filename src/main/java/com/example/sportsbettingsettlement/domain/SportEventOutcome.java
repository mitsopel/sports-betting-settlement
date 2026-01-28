package com.example.sportsbettingsettlement.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SportEventOutcome {
    String eventId;
    String eventName;
    String eventWinnerId;

    @JsonCreator
    public SportEventOutcome(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("eventName") String eventName,
        @JsonProperty("eventWinnerId") String eventWinnerId
    ) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventWinnerId = eventWinnerId;
    }
}