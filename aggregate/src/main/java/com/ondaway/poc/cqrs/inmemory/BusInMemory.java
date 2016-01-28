package com.ondaway.poc.cqrs.inmemory;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.ddd.Event;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public class BusInMemory implements Bus {
    
    Map<String, Consumer> handlers = new HashMap();
    
    @Override
    public <T> void registerHandler(String name, Consumer<T> handler) {
        // TODO: obtain command class name from T makes commandName parameter unnecesary.
        // TODO: is it possible ?? JavaGenerics Erasure could be a problem !?
        handlers.put(name, handler);
    }

    @Override
    public <T extends Command> void send(T command) {
        Consumer<T> handler = handlers.get(command.getClass().getName());
        handler.accept(command);
    }

    @Override
    public <T extends Event> void publish(T event) {
        Consumer<T> handler = handlers.get(event.getClass().getName());
        handler.accept(event);
    }

}
