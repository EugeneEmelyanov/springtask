package beans.daos;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * UserDTO: Dmytro_Babichev
 * Date: 20/2/16
 * Time: 6:50 PM
 */
public abstract class AbstractDAO {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {return sessionFactory.getCurrentSession();}

    protected Criteria createBlankCriteria(Class clazz) {return getCurrentSession().createCriteria(clazz);}
}
