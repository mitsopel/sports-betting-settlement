package com.example.sportsbettingsettlement.repository;

import com.example.sportsbettingsettlement.domain.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByEventId(String eventId);
}