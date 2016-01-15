package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.cqrs.EventStoreInMemory;
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
        EventStore eventStore = new EventStoreInMemory();
        Repository<Vehicle> vehicles = new EventRepository(eventStore);
        vehicleHandlers = new VehicleCommandHandlers(vehicles);

        vehicle = new Vehicle();
        vehicles.Save(vehicle);
    }

    @Test
    public void HandleActivateTest() {

        //Given inactive vehicle
        UUID activator = UUID.randomUUID();

        // When
        vehicleHandlers.handle(new Activate(vehicle.id, activator));

        // Then capture repository events ???
    }
}
