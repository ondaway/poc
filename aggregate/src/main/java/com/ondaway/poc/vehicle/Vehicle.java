package com.ondaway.poc.vehicle;

import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.vehicle.event.Activated;
import com.ondaway.poc.vehicle.event.LocationChanged;
import java.util.UUID;

/**
 *
 * @author ernesto
 */
public class Vehicle extends AggregateRoot {
    
    public final UUID id = UUID.randomUUID();
    
    boolean active = false;
    Float x = 0f;
    Float y = 0f;
    
    public void activate() {
        if (active)
            throw new IllegalStateException("vehicle already activated");
        applyEvent(new Activated(id));
    }
    
    public void changeLocation(Float x, Float y) {
        if (!active)
            throw new IllegalStateException("vehicle is inactive");
        applyEvent(new LocationChanged(x,y));
    }
    
    // TODO: should be private but reflections needs this to be public in order to invoke it
    public void apply(Activated event) {
        this.active = true;
    }
    
    // TODO: should be private but reflections needs this to be public in order to invoke it
    public void apply(LocationChanged event) {
        this.x = event.x;
        this.y = event.y;
    }
}
