package com.epam.serializers;

import com.epam.models.Auditorium;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by yauhen_yemelyanau on 7/26/17.
 */
public class AuditoriumDesirializer extends JsonDeserializer<Auditorium> {

    @Override
    public Auditorium deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new Auditorium(jsonParser.getIntValue());
    }
}
