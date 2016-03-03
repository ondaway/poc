package com.ondaway.poc.cqrs;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public interface Bus {

    <T> void registerHandler(String messageName, Consumer<T> handler);

    <T> CompletableFuture<Optional<String>> executeHandlerFor(T message);
}
