package com.example.sportsbettingsettlement.controller;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import com.example.sportsbettingsettlement.mapper.SportEventOutcomeMapper;
import com.example.sportsbettingsettlement.service.SportEventOutcomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sport/event-outcomes")
@RequiredArgsConstructor
public class SportEventOutcomeController {

    private final SportEventOutcomeService sportEventOutcomeService;
    private final SportEventOutcomeMapper sportEventOutcomeMapper;

    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody SportEventOutcomeDto sportEventOutcomeDto) {
        SportEventOutcome sportEventOutcome = sportEventOutcomeMapper.toDomain(sportEventOutcomeDto);
        sportEventOutcomeService.publish(sportEventOutcome);
        return ResponseEntity.ok("Sport event outcome published to Kafka");
    }
}
