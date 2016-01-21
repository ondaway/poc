package com.ondaway.poc.cqrs;

import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public interface Bus {

    <T extends Command> void registerCommandHandler(Consumer<T> handler);

}
