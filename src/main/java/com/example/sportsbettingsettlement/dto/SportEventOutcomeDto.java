package com.example.sportsbettingsettlement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class SportEventOutcomeDto {

    @NotNull
    String eventId;

    @NotNull
    String eventName;

    @NotNull
    String eventWinnerId;
}