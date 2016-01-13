package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;
import com.ondaway.poc.cqrs.EventStoreInMemory;
import com.ondaway.poc.vehicle.event.Activated;
import com.ondaway.poc.vehicle.event.LocationChanged;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jeroldan
 */
public class VehicleRepositoryTest {
    
    EventStore eventStore;
    EventRepository<Vehicle> repository;
    Vehicle vehicle;
    
    @Before
    public void setup() {
        eventStore = new EventStoreInMemory();
        repository = new EventRepository(eventStore);
        vehicle = new Vehicle();
    }
    
    @Test
    public void saveVehicleTest() throws Exception {

        //Given
        vehicle.mutate(new Activated(vehicle.id));
        vehicle.mutate(new LocationChanged(1f, 1f));

        // When
        repository.Save(vehicle);

        // Then
        Assert.assertEquals(1, eventStore.length());
        Assert.assertEquals(2, eventStore.getEventsFor(vehicle.id).size());
    }
    
    @Test
    public void getVehicleByIdTest() throws Exception {

        //Given
        saveVehicleTest();
        
        Vehicle v = repository.GetById(vehicle.id, new Vehicle());
        Assert.assertNotNull(v);
        //Assert.assertEquals(vehicle, v);
    }
    
}
