package com.ondaway.poc.cqrs.stack;

import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.event.EventStream;
import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.ddd.Event;
import java.util.function.Consumer;

/**
 *
 * @author jeroldan
 */
public abstract class BusAkka implements Bus {

    ActorSystem handlers;
    EventStream eventBus;
    
    public BusAkka() {
        this.handlers = new ActorSystemImpl(null, null, null, null, null);
        this.eventBus = handlers.eventStream();
    }
    
    @Override
    public <T> void registerHandler(String id, Consumer<T> handler) {
        //eventBus.subscribe(handlers.actorOf(null), handler);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
