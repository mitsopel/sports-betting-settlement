package com.example.sportsbettingsettlement.repository;

import com.example.sportsbettingsettlement.entity.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<BetEntity, Long> {

    List<BetEntity> findByEventId(String eventId);
}