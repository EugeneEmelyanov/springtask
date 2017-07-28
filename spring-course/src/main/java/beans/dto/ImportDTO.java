package beans.dto;

import com.epam.models.Event;
import com.epam.models.User;

import java.util.List;

/**
 * Created by Yauhen_Yemelyanau on 7/7/2017.
 */
public class ImportDTO {

    private List<Event> events;
    private List<User> users;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
