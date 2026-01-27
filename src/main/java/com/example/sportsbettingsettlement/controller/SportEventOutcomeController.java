package com.example.sportsbettingsettlement.controller;

import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import com.example.sportsbettingsettlement.service.SportEventOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sport/event-outcomes")
@RequiredArgsConstructor
public class SportEventOutcomeController {

    private final SportEventOutcomeService sportEventOutcomeService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody SportEventOutcomeDto sportEventOutcomeDto) {
        sportEventOutcomeService.publish(sportEventOutcomeDto);
        return ResponseEntity.ok("Sport event outcome published to Kafka");
    }
}
