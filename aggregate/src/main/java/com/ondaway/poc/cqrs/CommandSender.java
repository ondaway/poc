package com.ondaway.poc.cqrs;

/**
 *
 * @author ernesto
 */
public interface CommandSender {

    /**
     * 
     * @param <T>
     * @param command
     * @throws InvalidCommandException 
     */
    <T extends Command> void emit(T command) throws InvalidCommandException;
    
}
