package com.ondaway.poc.vehicle.event;

import com.ondaway.poc.ddd.Event;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author ernesto
 */
public class Activated implements Event {
    
    private final UUID id;

    public Activated(UUID id) {
        this.id = id;
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
