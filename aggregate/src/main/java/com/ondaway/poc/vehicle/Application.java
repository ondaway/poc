package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.cqrs.bus.BusInMemory;
import com.ondaway.poc.cqrs.eventstore.EventStoreInMemory;
import com.ondaway.poc.ddd.Repository;
import com.ondaway.poc.rest.API;
import com.ondaway.poc.vehicle.command.Activate;
import com.ondaway.poc.vehicle.command.Register;
import com.ondaway.poc.vehicle.command.ReportStatus;

/**
 *
 * @author jeroldan
 */
public class Application {

    public static void main(String[] args) {

        BusInMemory bus = new BusInMemory();
        EventStore store = new EventStoreInMemory(bus);
        Repository<Vehicle> vehicles = new EventRepository<>(store);
        VehicleCommandHandlers vehicleCommandHandlers = new VehicleCommandHandlers(vehicles);
        
        bus.registerHandler(Activate.class.getName(), vehicleCommandHandlers::handleActivate);
        bus.registerHandler(Register.class.getName(),vehicleCommandHandlers::handleRegister);
        bus.registerHandler(ReportStatus.class.getName(), vehicleCommandHandlers::handleReportStatus);
        
        API api = new API(bus);
        api.listen();
    }
}
