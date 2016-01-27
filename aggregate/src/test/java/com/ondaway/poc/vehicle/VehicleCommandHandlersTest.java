package com.ondaway.poc.vehicle;

import static com.ondaway.poc.bdd.CommandHandlerScenario.SCENARIO;
import com.ondaway.poc.bdd.World;
import com.ondaway.poc.cqrs.EventPublisher;
import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.cqrs.inmemory.EventStoreInMemory;
import com.ondaway.poc.ddd.Repository;
import com.ondaway.poc.vehicle.command.Activate;
import com.ondaway.poc.vehicle.event.Activated;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jeroldan
 */
public class VehicleCommandHandlersTest {

    World world;
    VehicleCommandHandlers vehicleHandlers;
    Vehicle vehicle;

    @Before
    public void Setup() {
        EventStore store = new EventStoreInMemory(null);
        Repository<Vehicle> vehicles = new EventRepository(store);
        vehicleHandlers = new VehicleCommandHandlers(vehicles);

        vehicle = new Vehicle();
        vehicles.Save(vehicle);
        
        world = new World(vehicles);
    }

    @Test
    public void HandleActivateTest() {

        UUID activator = UUID.randomUUID();
        Activate activate = new Activate(vehicle.id, activator);
        Activated activated = new Activated(vehicle.id);

        SCENARIO(world)
                .Given(vehicle.id)
                .When(vehicleHandlers::handleActivate).handles(activate)
                .Then(vehicle.id).shouldBe(activated);
    }
}
