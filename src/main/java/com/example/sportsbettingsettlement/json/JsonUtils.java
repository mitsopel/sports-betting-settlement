package com.example.sportsbettingsettlement.json;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public <T> T parseJson(final String json, final Class<T> targetType) {
        return objectMapper.readValue(json, targetType);
    }

    public String convertToJson(final Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
