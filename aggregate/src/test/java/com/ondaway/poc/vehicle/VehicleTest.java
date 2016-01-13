package com.ondaway.poc.vehicle;

import static org.junit.Assert.*;

import com.ondaway.poc.ddd.Event;
import com.ondaway.poc.vehicle.event.Activated;
import com.ondaway.poc.vehicle.event.LocationChanged;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ernesto
 */
public class VehicleTest {

    public VehicleTest() {
    }

    Vehicle vehicle;

    @Before
    public void setUp() {
        vehicle = new Vehicle();
    }

    @Test
    public void activateVehicleTest() throws Exception {
        
        // When
        vehicle.activate();
        
        // Then
        List<Event> events = vehicle.getPendingEvents();
        assertTrue(events.size() == 1);
        assertTrue(events.get(0).equals(new Activated(vehicle.id)));
    }

    @Test( expected = IllegalStateException.class)
    public void activateActiveVehicle() throws Exception {

        // Given
        vehicle.mutate(new Activated(vehicle.id));
        
        //When
        vehicle.activate();
        
        // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void moveInactiveVehicleTest() throws Exception {
        
        // When
        vehicle.changeLocation(1f, 1f);
        
        // Should throw IllegalStateException
    }

    @Test
    public void moveActiveVehicleTest() throws Exception {
        
        // Given
        vehicle.mutate(new Activated(vehicle.id));

        // When
        vehicle.changeLocation(1f, 1f);

        // Then
        List<Event> events = vehicle.getPendingEvents();
        assertTrue(events.size() == 2);
        assertTrue(events.get(1).equals(new LocationChanged(1f,1f)));
    }

}
