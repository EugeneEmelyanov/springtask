@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=LocalDateTime.class, value=LocalDateTimeAdapter.class)
})
package beans.models;

import beans.models.adapters.LocalDateTimeAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDateTime;

