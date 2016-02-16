package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.cqrs.inmemory.BusInMemory;
import com.ondaway.poc.cqrs.inmemory.EventStoreInMemory;
import com.ondaway.poc.ddd.Repository;
import com.ondaway.poc.rest.API;
import com.ondaway.poc.vehicle.command.Activate;

/**
 *
 * @author jeroldan
 */
public class Application {

    public static void main() {

        Bus bus = new BusInMemory();

        EventStore store = new EventStoreInMemory(bus);
        //TODO: bus.registerPublisher(store);

        Repository<Vehicle> vehicles = new EventRepository<>(store);
        VehicleCommandHandlers vehicleCommandHandlers = new VehicleCommandHandlers(vehicles);
        bus.registerHandler(Activate.class.getName(), vehicleCommandHandlers::handleActivate);

        API api = new API(bus);
        //TODO: bus.registerPublisher(api);
        api.listen();
    }
}
