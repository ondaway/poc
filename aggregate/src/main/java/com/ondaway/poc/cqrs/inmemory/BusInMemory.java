package com.ondaway.poc.cqrs.inmemory;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import java.util.function.Consumer;

/**
 *
 * @author ernesto
 */
public class BusInMemory implements Bus {

    @Override
    public <T extends Command> void registerCommandHandler(Consumer<T> handler) {
        //TODO
    }

   

}
