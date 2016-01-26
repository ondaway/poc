/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ondaway.poc.bdd;

import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.ddd.Event;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.junit.Assert;

/**
 *
 * @author ernesto
 */
public class CommandHandlerScenario {

    private final World world;
    private AggregateRoot subject;
    private Consumer handler;

    private CommandHandlerScenario(World world) {
        this.world = world;
    }

    public static CommandHandlerScenario SCENARIO(World world) {
        return new CommandHandlerScenario(world);
    }

    public CommandHandlerScenario Given(UUID id) {
        this.subject = this.world.find(id);
        return this;
    }

    public CommandHandlerScenario was(Event event) {
        this.subject.mutate(event);
        this.world.save(this.subject);
        return this;
    }
    
    public CommandHandlerScenario and(Event event) {
        return this.was(event);
    }    

    public <T extends Command> CommandHandlerScenario When(Consumer<T> action) {
        action.accept((T) this.subject);
        return this;
    }
    
    public <T extends Command> CommandHandlerScenario handles(T command) {
        if (this.handler != null)
            this.handler.accept(command);
        return this;
    }
    
    public <T extends AggregateRoot> CommandHandlerScenario Then(UUID id) {
        this.subject = this.world.find(id);
        return this;
    }

    public CommandHandlerScenario shouldBe(Event expected) {
        List<Event> pendingEvents = this.subject.getPendingEvents();
        boolean match = pendingEvents.stream().anyMatch( event -> {
            return event.equals(expected);
        });
        Assert.assertTrue(match);
        return this;
    }
}
