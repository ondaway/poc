package com.ondaway.poc.cqrs;

import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public interface Bus extends CommandSender, EventPublisher {

    <T> void registerHandler(String id, Consumer<T> handler);
    

}
