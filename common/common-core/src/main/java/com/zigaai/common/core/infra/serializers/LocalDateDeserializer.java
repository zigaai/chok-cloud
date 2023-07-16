package com.zigaai.common.core.infra.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zigaai.common.core.model.constants.DateTimeConstant;

import java.io.IOException;
import java.time.*;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        long timestamp = p.getValueAsLong();
        if (timestamp > 0) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), DateTimeConstant.UTC_ZONE_ID).toLocalDate();
        } else {
            return null;
        }
    }
}