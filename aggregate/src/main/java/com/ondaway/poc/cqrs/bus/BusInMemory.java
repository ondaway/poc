package com.ondaway.poc.cqrs.bus;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.cqrs.CommandSender;
import com.ondaway.poc.cqrs.InvalidCommandException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ernesto
 */
public class BusInMemory implements Bus, CommandSender {

    Map<String, Function> handlers = new HashMap();

    @Override
    public <T> void registerHandler(String name, Consumer<T> handler) {
        
        Function<T, Optional<String>> wrapper = (T message) -> {
            
            final CompletableFuture future = CompletableFuture.runAsync(() -> { 
                handler.accept(message);
            });
            
            try {
                future.get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                Logger.getLogger(BusInMemory.class.getName()).log(Level.SEVERE, null, ex);
                return Optional.of("error");
            }
            
            return Optional.empty();
        };
        
        handlers.put(name, wrapper);
    }

    @Override
    public <T extends Command> void emit(T command) throws InvalidCommandException {
        Optional<String> error = this.executeHandlerFor(command);
        if (error.isPresent()) {
            throw new InvalidCommandException();
        }
    }

    @Override
    public <T> Optional<String> executeHandlerFor(T message) {
        return handlerFor(message).apply(message);
    }

    private <T> Function<T, Optional<String>> handlerFor(T message) {
        String mssgName = message.getClass().getName();
        System.out.println(mssgName);
        Function<T, Optional<String>> handler = handlers.getOrDefault(mssgName, (m) -> {
            return Optional.of("Handler not found for : " + m);
        });
        return handler;
    }
}
