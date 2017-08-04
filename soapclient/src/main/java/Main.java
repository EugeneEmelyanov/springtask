import com.epam.soapclient.EventSOAPClient;
import wsdl.EventDTO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<EventDTO> eventDTOS = new EventSOAPClient().getEvents();

        System.out.println(eventDTOS);
    }
}
