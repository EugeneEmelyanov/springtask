@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=LocalDateTime.class, value=LocalDateTimeAdapter.class)
})
@XmlSchemaTypes({
        @XmlSchemaType(name="date", type=LocalDate.class)
        ,@XmlSchemaType(name="dateTime", type=LocalDateTime.class)
        ,@XmlSchemaType(name="time", type=LocalTime.class)
})
package com.epam.ws.model;

import com.epam.models.adapters.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSchemaTypes;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

