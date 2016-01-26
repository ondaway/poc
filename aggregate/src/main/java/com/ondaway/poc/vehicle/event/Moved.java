package com.ondaway.poc.vehicle.event;

import com.ondaway.poc.ddd.Event;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author ernesto
 */
public class Moved implements Event {
    
    public final UUID id;
    public final Float x;
    public final Float y;
    public final long timestamp;

    public Moved(UUID id, Float x, Float y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.timestamp = new Timestamp(new Date().getTime()).getTime();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Moved other = (Moved) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        if (!Objects.equals(this.y, other.y)) {
            return false;
        }
        return true;
    }


    
}
