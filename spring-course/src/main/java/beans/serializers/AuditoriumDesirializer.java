package beans.serializers;

import beans.models.Auditorium;
import beans.services.AuditoriumService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

/**
 * Created by yauhen_yemelyanau on 7/26/17.
 */
public class AuditoriumDesirializer extends JsonDeserializer<Auditorium> {

    @Autowired
    @Qualifier("auditoriumServiceImpl")
    private AuditoriumService auditoriumService;
    @Override
    public Auditorium deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new Auditorium(jsonParser.getIntValue());
    }
}
