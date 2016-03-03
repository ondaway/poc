package com.ondaway.poc.cqrs;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author ernesto
 */
public interface CommandSender {

    /**
     * 
     * @param <T>
     * @param message
     * @return 
     */
    <T> CompletableFuture<Optional<String>> emit(T message);
    
}
