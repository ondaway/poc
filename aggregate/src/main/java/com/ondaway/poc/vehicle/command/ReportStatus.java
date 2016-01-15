package com.ondaway.poc.vehicle.command;

import java.util.UUID;

/**
 *
 * @author jeroldan
 */
public class ReportStatus {
    
    public final UUID vehicle;
    public final boolean active;
    public final float x, y;

    public ReportStatus(UUID vehicle, boolean active, float x, float y) {
        this.vehicle = vehicle;
        this.active = active;
        this.x = x;
        this.y = y;
    }
    
}
