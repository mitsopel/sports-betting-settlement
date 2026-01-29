package com.example.sportsbettingsettlement.mapper;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.dto.BetDto;
import com.example.sportsbettingsettlement.entity.BetEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BetMapper {

    Bet toDomain(BetEntity entity);
    List<Bet> toDomainList(List<BetEntity> entities);

    BetDto toDto(Bet domain);
    List<BetDto> toDtoList(List<Bet> domains);
}
