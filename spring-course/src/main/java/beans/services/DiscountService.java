package beans.services;

import com.epam.models.Event;
import com.epam.models.User;

/**
 * Created with IntelliJ IDEA.
 * UserDTO: Dmytro_Babichev
 * Date: 2/4/2016
 * Time: 11:17 AM
 */
public interface DiscountService {

    double getDiscount(User user, Event event);
}
