package com.example.sportsbettingsettlement.repository;

import com.example.sportsbettingsettlement.entity.BetEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<BetEntity, Long> {

    List<BetEntity> findByEventId(String eventId);
}