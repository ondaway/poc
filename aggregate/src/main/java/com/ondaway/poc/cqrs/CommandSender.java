package com.ondaway.poc.cqrs;

/**
 *
 * @author ernesto
 */
public interface CommandSender {

    public <T extends Command> void send(T command);
    
}
