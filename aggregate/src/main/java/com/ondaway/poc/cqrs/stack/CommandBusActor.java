package com.ondaway.poc.cqrs.stack;

import akka.actor.UntypedActor;
import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.ddd.Event;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author jeroldan
 */
public class CommandBusActor extends UntypedActor implements Bus {

    Map<String, Consumer> handlers = new HashMap();

    @Override
    public <T> void registerHandler(String id, Consumer<T> handler) {
        handlers.put(id, handler);
    }

    @Override
    public void onReceive(Object message) throws Exception {        
        if (message instanceof Command) {
            handlers.get(message.getClass().getName()).accept(message);
        }
    }

    @Override
    public <T extends Command> void send(T command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T extends Event> void publish(T event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
