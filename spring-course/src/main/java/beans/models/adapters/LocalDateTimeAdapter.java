package beans.models.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDateTime;

/**
 * Created by yauhen_yemelyanau on 7/19/17.
 */

public class LocalDateTimeAdapter
        extends XmlAdapter<String, LocalDateTime> {

    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v);
    }

    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }

}
