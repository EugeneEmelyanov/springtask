package com.epam.ws.model.requests;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(
        name = "EventRequest",
        namespace = "http://epam.com/springcourse/"
)
public class EventRequest {
    private long id;

    public EventRequest() {
    }

    @XmlElement
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}