package com.example.sportsbettingsettlement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.sportsbettingsettlement.domain.Bet;
import com.example.sportsbettingsettlement.dto.BetDto;
import com.example.sportsbettingsettlement.mapper.BetMapper;
import com.example.sportsbettingsettlement.service.BetService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BetController.class)
class BetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BetService betService;

    @MockBean
    private BetMapper betMapper;

    @Test
    void shouldReturnListOnFindAll() throws Exception {
        List<Bet> domain = List.of(
            new Bet(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN),
            new Bet(2L, 102L, "EVT-2", "MKT-2", "WIN-2", BigDecimal.ONE)
        );
        when(betService.findAll()).thenReturn(domain);
        when(betMapper.toDtoList(domain)).thenReturn(List.of(
            new BetDto(1L, 101L, "EVT-1", "MKT-1", "WIN-1", BigDecimal.TEN),
            new BetDto(2L, 102L, "EVT-2", "MKT-2", "WIN-2", BigDecimal.ONE)
        ));

        mockMvc.perform(get("/bets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].betId").value(1))
            .andExpect(jsonPath("$[1].eventId").value("EVT-2"));
    }
}
