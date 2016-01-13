package com.ondaway.poc.cqrs;

import com.ondaway.poc.ddd.Event;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeroldan
 */
public interface EventStore {

    public void save(UUID id, List<Event> events);

    public List<Event> getEventsFor(UUID id);

    public int length();
    
}
