package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.EventPublisher;
import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.cqrs.inmemory.EventStoreInMemory;
import com.ondaway.poc.ddd.Repository;
import com.ondaway.poc.vehicle.command.Activate;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jeroldan
 */
public class VehicleCommandHandlersTest {

    VehicleCommandHandlers vehicleHandlers;
    Vehicle vehicle;

    @Before
    public void Setup() {
        EventStore store = new EventStoreInMemory(null);
        Repository<Vehicle> vehicles = new EventRepository(store);
        vehicleHandlers = new VehicleCommandHandlers(vehicles);

        vehicle = new Vehicle();
        vehicles.Save(vehicle);
    }

    @Test
    public void HandleActivateTest() {

        //Given inactive vehicle
        UUID activator = UUID.randomUUID();

        // When
        vehicleHandlers.handleActivate(new Activate(vehicle.id, activator));

        // Then capture repository events ???
    }
}
