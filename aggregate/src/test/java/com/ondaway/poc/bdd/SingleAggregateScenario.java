/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ondaway.poc.bdd;

import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.ddd.Event;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Assert;

/**
 *
 * @author ernesto
 */
public class SingleAggregateScenario {

    private AggregateRoot subject;

    private SingleAggregateScenario() {
    
    }

    public static SingleAggregateScenario SCENARIO() {
        return new SingleAggregateScenario();
    }

    public <T extends AggregateRoot> SingleAggregateScenario Given(T subject) {
        this.subject = subject;
        return this;
    }

    public SingleAggregateScenario was(Event event) {
        this.subject.mutate(event);
        return this;
    }
    
    public SingleAggregateScenario and(Event event) {
        return this.was(event);
    }    

    public <T extends AggregateRoot> SingleAggregateScenario When(Consumer<T> action) {
        action.accept((T) this.subject);
        return this;
    }
    
    public <T extends AggregateRoot> SingleAggregateScenario Then(T subject) {
        return this;
    }

    public SingleAggregateScenario shouldBe(Event expected) {
        List<Event> pendingEvents = subject.getPendingEvents();
        boolean match = pendingEvents.stream().anyMatch( event -> {
            return event.equals(expected);
        });
        Assert.assertTrue(match);
        return this;
    }
}
