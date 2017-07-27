package beans.dto;

import beans.models.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Created by yauhen_yemelyanau on 7/13/17.
 */
public class EventSeatsDTO {

    private Event event;
    private int numRegularSeats;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getNumRegularSeats() {
        return numRegularSeats;
    }

    public void setNumRegularSeats(int numRegularSeats) {
        this.numRegularSeats = numRegularSeats;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public int getNumVipSeats() {
        return numVipSeats;
    }

    public void setNumVipSeats(int numVipSeats) {
        this.numVipSeats = numVipSeats;
    }

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    private double regularPrice;

    private List<Integer> regularSeats;

    private int numVipSeats;
    private double vipPrice;

    private List<Integer> vipSeats;

    public List<Integer> getRegularSeats() {
        return regularSeats;
    }

    public void setRegularSeats(List<Integer> regularSeats) {
        this.regularSeats = regularSeats;
    }

    public List<Integer> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(List<Integer> vipSeats) {
        this.vipSeats = vipSeats;
    }

    private EventSeatsDTO(Builder builder) {
        event = builder.event;
        numRegularSeats = builder.numRegularSeats;
        setRegularSeats(builder.regularSeats);
        numVipSeats = builder.numVipSeats;
        vipPrice = builder.vipPrice;
        setVipSeats(builder.vipSeats);
        regularPrice = builder.regularPrice;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

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
        return new HashCodeBuilder(1, 23)
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

    public static final class Builder {
        private Event event;
        private int numRegularSeats;
        private List<Integer> regularSeats;
        private double regularPrice;
        private int numVipSeats;
        private double vipPrice;
        private List<Integer> vipSeats;

        private Builder() {
        }

        public Builder withEvent(Event val) {
            event = val;
            return this;
        }

        public Builder withNumRegularSeats(int val) {
            numRegularSeats = val;
            return this;
        }

        public Builder withRegularSeats(List<Integer> val) {
            regularSeats = val;
            return this;
        }

        public Builder withRegularPrice(double val) {
            regularPrice = val;
            return this;
        }

        public Builder withNumVipSeats(int val) {
            numVipSeats = val;
            return this;
        }

        public Builder withVipPrice(double val) {
            vipPrice = val;
            return this;
        }

        public Builder withVipSeats(List<Integer> val) {
            vipSeats = val;
            return this;
        }

        public EventSeatsDTO build() {
            return new EventSeatsDTO(this);
        }
    }
}
