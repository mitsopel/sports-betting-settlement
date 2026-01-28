package com.example.sportsbettingsettlement.mapper;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.example.sportsbettingsettlement.dto.SportEventOutcomeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SportEventOutcomeMapper {

    SportEventOutcome toDomain(SportEventOutcomeDto dto);
}
