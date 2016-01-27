package com.ondaway.poc.vehicle;


import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.vehicle.event.Activated;
import com.ondaway.poc.vehicle.event.Created;
import com.ondaway.poc.vehicle.event.Moved;

/**
 *
 * @author ernesto
 */
public class Vehicle extends AggregateRoot {

    boolean active = false;

    public Vehicle() {
        mutate(new Created());
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
    
    // TODO: should be private but reflections needs this to be public in order to invoke it
    public void apply(Activated event) {
        this.active = true;
    }
    
}
