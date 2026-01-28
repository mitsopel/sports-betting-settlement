package com.example.sportsbettingsettlement.repository;

import com.example.sportsbettingsettlement.domain.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//todo should i add @repository? ti einai to JpaRepository<Bet, Long>??
public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByEventId(String eventId);
}