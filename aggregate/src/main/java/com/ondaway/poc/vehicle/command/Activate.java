package com.ondaway.poc.vehicle.command;

import com.ondaway.poc.cqrs.Command;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author jeroldan
 */
public class Activate implements Command {

    public final UUID vehicle;
    public final UUID activator;
    public final long timestamp;

    public Activate(UUID vehicle, UUID activator) {
        this.vehicle = vehicle;
        this.activator = activator;
        this.timestamp = new Timestamp(new Date().getTime()).getTime();
    }
}
