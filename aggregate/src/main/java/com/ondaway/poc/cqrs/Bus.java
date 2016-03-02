package com.ondaway.poc.cqrs;

import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public interface Bus {

    <T> void registerHandler(String messageName, Consumer<T> handler);
    
    /**
     * 
     * @param <T>
     * @param message
     * @return Optional<String> error
     */
    <T> Optional<String> executeHandlerFor(T message);
}
