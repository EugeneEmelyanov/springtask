package beans;

import com.epam.models.Ticket;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by yauhen_yemelyanau on 7/19/17.
 */
public class PdfMessageConverter extends AbstractGenericHttpMessageConverter<Object> {


    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if ( mediaType != null && "application/pdf".equals(mediaType.toString())) {
            return true;
        }
        return super.canWrite(clazz, mediaType);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz == Ticket.class;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }
}
