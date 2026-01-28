package com.example.sportsbettingsettlement.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sportsbettingsettlement.domain.SportEventOutcome;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonUtilsTest {

    private JsonUtils jsonUtils;

    @BeforeEach
    void setUp() {
        jsonUtils = new JsonUtils(new ObjectMapper());
    }

    @Test
    void shouldSerializeAndDeserializeRoundTrip() {
        SportEventOutcome in = new SportEventOutcome("EVT-1", "Event One", "WIN-1");

        String json = jsonUtils.convertToJson(in);
        SportEventOutcome out = jsonUtils.parseJson(json, SportEventOutcome.class);

        assertThat(out).isEqualTo(in);
    }
}
