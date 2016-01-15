package com.ondaway.poc.vehicle.command;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author jeroldan
 */
public class Activate {

    public final UUID vehicle;
    public final UUID activator;
    public final long timestamp;

    public Activate(UUID vehicle, UUID activator) {
        this.vehicle = vehicle;
        this.activator = activator;
        this.timestamp = new Timestamp(new Date().getTime()).getTime();
    }
}
