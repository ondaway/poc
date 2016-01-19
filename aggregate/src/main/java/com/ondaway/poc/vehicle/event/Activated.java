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
public class Activated implements Event {
    
    private final UUID id;
    public final long timestamp;

    public Activated(UUID id) {
        this.id = id;
        this.timestamp = new Timestamp(new Date().getTime()).getTime();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Activated other = (Activated) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
