package com.ondaway.poc.vehicle.event;

import com.ondaway.poc.ddd.Event;

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
    
}
