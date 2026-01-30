package com.example.sportsbettingsettlement.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import com.example.sportsbettingsettlement.mapper.SportEventOutcomeMapper;
import com.example.sportsbettingsettlement.service.SportEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sport-event")
@RequiredArgsConstructor
public class SportEventController {

    private final SportEventService sportEventService;
    private final SportEventOutcomeMapper sportEventOutcomeMapper;

    @PostMapping(path = "/outcomes", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@Valid @RequestBody SportEventOutcomeDto sportEventOutcomeDto) {
        SportEventOutcome sportEventOutcome = sportEventOutcomeMapper.toDomain(sportEventOutcomeDto);
        sportEventService.add(sportEventOutcome);
        return ResponseEntity.ok("Sport event outcome published to Kafka");
    }
}
