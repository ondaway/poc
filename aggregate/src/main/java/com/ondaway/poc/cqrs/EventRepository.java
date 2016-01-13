package com.ondaway.poc.cqrs;

import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.ddd.Event;
import com.ondaway.poc.ddd.Repository;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeroldan
 * @param <T>
 */
public class EventRepository<T extends AggregateRoot> implements Repository<T> {

    EventStore store;

    public EventRepository(EventStore store) {
        this.store = store;
    }

    @Override
    public void Save(AggregateRoot aggregate) {
        this.store.save(aggregate.id, aggregate.getPendingEvents());
    }

    @Override
    public T GetById(UUID id, T instance) {
        List<Event> events = this.store.getEventsFor(id);
        events.stream().forEach(event -> {
            instance.mutate(event);
        });
        return instance;
    }
    
}
