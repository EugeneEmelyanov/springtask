package wsdl;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(name = "EventRequest",
        namespace = "http://epam.com/springcourse/")
public class EventRequest {

    private long id;

    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
