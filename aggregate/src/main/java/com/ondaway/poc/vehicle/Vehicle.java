package com.ondaway.poc.vehicle;

import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.vehicle.event.Activated;
import com.ondaway.poc.vehicle.event.Created;
import com.ondaway.poc.vehicle.event.Moved;
import java.util.UUID;

/**
 *
 * @author ernesto
 */
public class Vehicle extends AggregateRoot {

    boolean active = false;

    public Vehicle() {}
    
    public Vehicle(UUID id) {
        mutate(new Created(id));
    }
    
    public void activate() {
        if (active) {
            throw new IllegalStateException("vehicle already activated");
        }
        mutate(new Activated(id));
    }

    public void move(Float x, Float y) {
        if (!active) {
            throw new IllegalStateException("vehicle is inactive");
        }
        mutate(new Moved(this.id, x, y));
    }
    
    public void apply(Created event) {
        this.id = event.id;
    }
    
    public void apply(Activated event) {
        this.active = true;
    }
    
}
