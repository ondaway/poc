package com.ondaway.poc.cqrs.bus;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.CommandSender;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import static java.util.concurrent.CompletableFuture.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ernesto
 */
public class BusInMemory implements Bus, CommandSender {

    Map<String, Consumer> handlers = new HashMap();

    @Override
    public <T> void registerHandler(String name, Consumer<T> handler) {
        handlers.put(name, handler);
    }

    @Override
    public <T> CompletableFuture<Optional<String>> emit(T message) {
        return executeHandlerFor(message);
    }

    public <T> CompletableFuture<Optional<String>> emit(T message, Consumer<Optional<String>> callback) {
        CompletableFuture future = emit(message);
        future.thenAccept(callback);
        return future;
    }

    @Override
    public <T> CompletableFuture<Optional<String>> executeHandlerFor(T message) {

        Consumer<T> handler = handlerFor(message);

        CompletableFuture<Optional<String>> future = supplyAsync(() -> {
            try {
                handler.accept(message);
            } catch (Exception ex) {
                Logger.getLogger(BusInMemory.class.getName()).log(Level.SEVERE, null, ex);
                return Optional.of("ERROR : " + ex.getClass().getName());
            }
            return Optional.empty();
        });

        return future;
    }

    private <T> Consumer<T> handlerFor(T message) {
        String mssgName = message.getClass().getName();
        Consumer<T> handler = handlers.getOrDefault(mssgName, (mssg) -> {
            throw new IllegalArgumentException("Handler not found for : " + mssgName);
        });
        return handler;
    }

}
