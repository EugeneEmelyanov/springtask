package com.epam.ws.model.converters;

import com.epam.models.Event;
import com.epam.ws.model.EventDTO;

import java.util.Objects;

public class EventConverter {

    public static EventDTO convertFromModelToWs(Event sourceEvent) {
        Objects.requireNonNull(sourceEvent);

        EventDTO targetEventDTO = new EventDTO();
        targetEventDTO.setAuditoriumId(sourceEvent.getAuditorium().getId());
        targetEventDTO.setBasePrice(sourceEvent.getBasePrice());
        targetEventDTO.setDateTime(sourceEvent.getDateTime());
        targetEventDTO.setId(sourceEvent.getId());
        targetEventDTO.setName(sourceEvent.getName());
        targetEventDTO.setRate(sourceEvent.getRate().name());

        return targetEventDTO;
    }
}
