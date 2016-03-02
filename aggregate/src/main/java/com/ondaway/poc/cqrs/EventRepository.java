package com.ondaway.poc.cqrs;

import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.ddd.Event;
import com.ondaway.poc.ddd.Factory;
import com.ondaway.poc.ddd.Repository;
import java.util.List;
import java.util.Optional;
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
    public void save(AggregateRoot aggregate) {
        this.store.save(aggregate.id, aggregate.getPendingEvents());
    }

    @Override
    public Optional<T> getById(UUID id, Class clazz) {
        try {
            Factory<T> factory = Factory.createFactory(clazz);
            T instance = factory.create();
            List<Event> events = this.store.getEventsFor(id);
            events.stream().forEach(event -> {
                instance.mutate(event);
            });
            return Optional.of(instance);
        } catch (InstantiationException ex) {
            return Optional.empty();
        } catch (IllegalAccessException ex) {
            return Optional.empty();
        }
    }

}
