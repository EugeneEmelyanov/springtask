package beans.daos.mocks;

import beans.daos.AuditoriumDAO;
import com.epam.models.Auditorium;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 06/2/16
 * Time: 1:27 PM
 */
public class DBAuditoriumDAOMock implements AuditoriumDAO {

    private final List<Auditorium> auditoriums;

    public DBAuditoriumDAOMock(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public void init() {
    }

    public void cleanup() {

    }

    @Override
    public List<Auditorium> getAll() {
        return auditoriums;
    }

    @Override
    public Auditorium getByName(String name) {
        return auditoriums.stream()
                .filter(auditorium -> auditorium.getName().equals(name))
                .findFirst().get();
    }

    @Override
    public void delete(Auditorium auditorium) {
        auditoriums.remove(auditorium);
    }

    @Override
    public Auditorium add(Auditorium auditorium) {
         auditoriums.add(auditorium);
         return auditorium;
    }
}
