package com.ondaway.poc.vehicle.event;

import com.ondaway.poc.ddd.Event;
import java.util.Objects;

/**
 *
 * @author ernesto
 */
public class LocationChanged implements Event {
    
    public final Float x;
    public final Float y;

    public LocationChanged(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LocationChanged other = (LocationChanged) obj;
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        if (!Objects.equals(this.y, other.y)) {
            return false;
        }
        return true;
    }
    
}
