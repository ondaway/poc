package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.inmemory.BusInMemory;
import com.ondaway.poc.vehicle.command.Activate;

/**
 *
 * @author jeroldan
 */
public class Application {
    
    public static void main() {
        Bus bus = new BusInMemory();
        VehicleCommandHandlers vehicleCommandHandlers = new VehicleCommandHandlers((null));
        bus.registerHandler(Activate.class.getName(), vehicleCommandHandlers::handleActivate);
        
    }
}
