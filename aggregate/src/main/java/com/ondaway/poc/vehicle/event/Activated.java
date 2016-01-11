package com.ondaway.poc.vehicle.event;

import com.ondaway.poc.ddd.Event;

/**
 *
 * @author ernesto
 */
public class Activated implements Event {

    public Activated() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}
