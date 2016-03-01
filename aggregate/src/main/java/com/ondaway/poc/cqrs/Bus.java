package com.ondaway.poc.cqrs;

import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public interface Bus extends CommandSender {

    <T> void registerHandler(String messageName, Consumer<T> handler);
    
}
