package com.ondaway.poc.cqrs;

import com.ondaway.poc.ddd.Event;

/**
 *
 * @author ernesto
 */
public interface EventPublisher {
    
    <T extends Event> void publish(T event);
    
}
