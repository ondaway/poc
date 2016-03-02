package com.ondaway.poc.vehicle.event;

import com.ondaway.poc.ddd.Event;
import java.util.UUID;

/**
 *
 * @author ernesto
 */
public class Created implements Event {
    
    public final UUID id;
    
    public Created(UUID id) {
        this.id = id;
    }
}
