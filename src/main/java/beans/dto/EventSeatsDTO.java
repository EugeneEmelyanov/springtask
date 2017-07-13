package beans.dto;

import beans.models.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by yauhen_yemelyanau on 7/13/17.
 */
public class EventSeatsDTO {

    private Event event;
    private int numRegularSeats;
    private double regularPrice;

    private int numVipSeats;
    private double vipPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventSeatsDTO that = (EventSeatsDTO) o;

        return new EqualsBuilder()
                .append(event, that.event)
                .append(numRegularSeats, that.numRegularSeats)
                .append(regularPrice, that.regularPrice)
                .append(numVipSeats, that.numVipSeats)
                .append(vipPrice, that.vipPrice)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1,23)
                .append(event)
                .append(numRegularSeats)
                .append(regularPrice)
                .append(numVipSeats)
                .append(vipPrice)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "EventSeatsDTO{" +
                "event=" + event +
                ", numRegularSeats=" + numRegularSeats +
                ", regularPrice=" + regularPrice +
                ", numVipSeats=" + numVipSeats +
                ", vipPrice=" + vipPrice +
                '}';
    }
}
