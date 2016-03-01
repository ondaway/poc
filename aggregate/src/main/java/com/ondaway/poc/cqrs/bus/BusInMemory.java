package com.ondaway.poc.cqrs.bus;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.cqrs.InvalidCommandException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public class BusInMemory implements Bus {

    Map<String, Consumer> handlers = new HashMap();
    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public <T> void registerHandler(String name, Consumer<T> handler) {
        handlers.put(name, handler);
    }

    @Override
    public <T extends Command> void emit(T command) throws InvalidCommandException {
        _executeHandlerFor(command); 
    }

    private <T> void _executeHandlerFor(T message) throws InvalidCommandException {
        String mssgName = message.getClass().getName();
        _parallel(_handlerFor(mssgName), message);
    }

    private <T> void _parallel(Consumer handler, T message) {
        executor.submit(() -> {
            handler.accept(message);
        });
    }

    private Consumer _handlerFor(String mssgName) throws InvalidCommandException {
        if (!handlers.keySet().contains(mssgName)) {
            throw new InvalidCommandException();
        }
        return handlers.get(mssgName);
    }

}
