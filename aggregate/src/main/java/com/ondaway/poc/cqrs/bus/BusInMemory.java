package com.ondaway.poc.cqrs.bus;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.cqrs.CommandSender;
import com.ondaway.poc.cqrs.InvalidCommandException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import static java.util.concurrent.CompletableFuture.*;
import java.util.concurrent.ExecutionException;
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

        Function<T, CompletableFuture<Optional<String>>> wrapper = (T message) -> {
            final CompletableFuture<Optional<String>> future = supplyAsync(() -> {
                try {
                    handler.accept(message);
                } catch (Exception ex) {
                    Logger.getLogger(BusInMemory.class.getName()).log(Level.SEVERE, null, ex);
                    return Optional.of("ERROR : " + ex.getCause());
                }
                return Optional.empty();
            });
            return future;
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
        
        CompletableFuture<Optional<String>> future = handlerFor(message).apply(message);
        try {
            Optional<String> result = future.get();
            return result;
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(BusInMemory.class.getName()).log(Level.SEVERE, null, ex);
            return Optional.of("ERROR : " + ex.getCause());
        }
        
    }

    private <T> Function<T, CompletableFuture<Optional<String>>> handlerFor(T message) {

        String mssgName = message.getClass().getName();

        Function<T, CompletableFuture<Optional<String>>> defaultWrapper = (T m) -> {
            final CompletableFuture<Optional<String>> future = supplyAsync(() -> {
                return Optional.of("ERROR : Handler Not Found");
            });
            return future;
        };
        
        return handlers.getOrDefault(mssgName, defaultWrapper);
    }
}
