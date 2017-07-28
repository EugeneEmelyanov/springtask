package com.epam.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Yauhen_Yemelyanau on 7/7/2017.
 */
public class LocalDateTimeDesirializer extends StdDeserializer<LocalDateTime> {
    private static final long serialVersionUID = 1L;

    protected LocalDateTimeDesirializer() {
        super(LocalDateTime.class);
    }


    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return LocalDateTime.parse(jp.readValueAs(String.class));
    }
}
