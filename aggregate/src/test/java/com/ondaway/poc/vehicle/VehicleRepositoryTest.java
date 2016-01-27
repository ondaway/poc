package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.cqrs.inmemory.EventStoreInMemory;
import com.ondaway.poc.vehicle.event.Activated;
import com.ondaway.poc.vehicle.event.Moved;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jeroldan
 */
public class VehicleRepositoryTest {

    EventStore store;
    EventRepository<Vehicle> repository;
    Vehicle vehicle;

    @Before
    public void setup() {
        store = new EventStoreInMemory(null);
        repository = new EventRepository(store);
        vehicle = new Vehicle();
    }

    @Test
    public void saveVehicleTest() throws Exception {

        //Given
        vehicle.mutate(new Activated(vehicle.id));
        vehicle.mutate(new Moved(vehicle.id, 1f, 1f));

        // When
        repository.Save(vehicle);

        // Then
        assertEquals(1, store.length());
        assertEquals(3, store.getEventsFor(vehicle.id).size());
    }

    @Test
    public void getVehicleByIdTest() throws Exception {

        //Given
        saveVehicleTest();

        Vehicle v = repository.GetById(vehicle.id, Vehicle.class).get();
        assertNotNull(v);
        //Assert.assertEquals(vehicle, v);
    }

}
