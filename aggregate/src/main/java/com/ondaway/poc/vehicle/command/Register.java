package com.ondaway.poc.vehicle.command;

import com.ondaway.poc.cqrs.Command;
import java.util.UUID;

/**
 *
 * @author jeroldan
 */
public class Register implements Command {
    
    public final UUID id;
    
    public Register(final UUID id) {
        this.id = id;
    }
    
}
