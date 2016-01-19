package com.ondaway.poc.cqrs;

import com.ondaway.poc.ddd.Event;

/**
 *
 * @author ernesto
 */
public interface EventPublisher {
    
    void publish(Event event);
}
