package com.ondaway.poc.cqrs.inmemory;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.EventPublisher;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.ddd.Event;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author jeroldan
 */
public class EventStoreInMemory implements EventStore, EventPublisher {

    Map<UUID, List<Event>> streams = new HashMap();
    Bus bus;
    
    public EventStoreInMemory(Bus bus) {
        this.bus = bus;
    }
    
    @Override
    public void save(UUID id, List<Event> events) {
        if (streams.containsKey(id)) {
            streams.get(id).addAll(events);
        } else {
            streams.put(id, events);
        }
        events.stream().forEach( event -> publish(event));
    }

    @Override
    public List<Event> getEventsFor(UUID id) {
        if (!streams.containsKey(id))
            throw new IllegalArgumentException();
        return streams.get(id);
    }

    @Override
    public int length() {
        return streams.size();
    }

    @Override
    public <T extends Event> void publish(T event) {
        //this.bus.send(event);
    }
    
}
