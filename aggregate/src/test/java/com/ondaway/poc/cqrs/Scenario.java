package com.ondaway.poc.cqrs;

import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.ddd.Event;
import java.util.List;
import java.util.function.Consumer;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author jeroldan
 */
public class Scenario {

    private AggregateRoot subject;
    private Consumer handler;

    private Scenario(AggregateRoot subject) {
        this.subject = subject;
    }
    
    public static Scenario Given(AggregateRoot aggregate) {
        return new Scenario(aggregate);
    }
        
    public <T extends Command> Scenario When(Consumer<T> handler) {
        this.handler = handler;
        return this;
    }

    public <T extends AggregateRoot> Scenario OnAction(Consumer<T> action) {
       action.accept((T) subject);
       return this;
    }

    public Scenario Then(AggregateRoot aggregate) {
        return this;
    }    
    
    public Scenario was(Event event) {
        subject.mutate(event);
        return this;
    }

    public Scenario and(Event event) {
        return was(event);
    }
    
    public <T extends Command> Scenario handles(T command) {
        if (handler != null)
            handler.accept(command);
        return this;
    }

    public Scenario shouldBe(Event expected) {
        List<Event> events = subject.getPendingEvents();
        boolean match = events.stream().anyMatch(event -> {
            return event.equals(expected);
        });
        assertTrue(match);
        return this;
    }

}
