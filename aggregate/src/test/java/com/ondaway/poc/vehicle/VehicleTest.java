package com.ondaway.poc.vehicle;

import static com.ondaway.poc.bdd.SingleAggregateScenario.SCENARIO;
import com.ondaway.poc.vehicle.event.Activated;
import com.ondaway.poc.vehicle.event.Moved;
import java.util.UUID;

import org.junit.Before;
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
        vehicle = new Vehicle(UUID.randomUUID());
    }
    
    @Test
    public void activateVehicleTest() throws Exception {

        Activated activated = new Activated(vehicle.id);
        
        SCENARIO("activate vehicle")
                .Given(vehicle)
                .When((Vehicle v) -> {
                    v.activate();
                })
                .Then(vehicle).shouldBe(activated);
    }

    @Test(expected = IllegalStateException.class)
    public void activateActiveVehicle() throws Exception {

        Activated activated = new Activated(vehicle.id);
        
        SCENARIO("activate activated vehicle")
                .Given(vehicle).was(activated)
                .When((Vehicle v) -> {
                    v.activate();
                }); // Should throw IllegalStateException
    }

    @Test(expected = IllegalStateException.class)
    public void moveInactiveVehicleTest() throws Exception {
        // When
        vehicle.move(1f, 1f); // Should throw IllegalStateException
    }

    @Test
    public void moveActiveVehicleTest() throws Exception {

        Activated activated = new Activated(vehicle.id);
        Moved moved = new Moved(vehicle.id, 1f, 1f);

        SCENARIO("move active vehicle")
                .Given(vehicle).was(activated)
                .When((Vehicle v) -> { v.move(1f, 1f); })
                .Then(vehicle)
                    .shouldBe(moved);
    }

}
