package com.example.sportsbettingsettlement.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T parseJson(final String json, final Class<T> targetType) {
        return objectMapper.readValue(json, targetType);
    }

    @SneakyThrows
    public String convertToJson(final Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
