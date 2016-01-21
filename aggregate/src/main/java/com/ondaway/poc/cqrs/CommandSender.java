package com.ondaway.poc.cqrs;

/**
 *
 * @author ernesto
 */
public interface CommandSender {
    
    public void send(Command command);
}
