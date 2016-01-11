import com.ondaway.poc.vehicle.Vehicle;
import com.ondaway.poc.vehicle.event.Activated;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ernesto
 */
public class VehicleTest {

    public VehicleTest() {
    }

    Vehicle vehicle;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        vehicle = new Vehicle();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void activateVehicleTest() throws Exception {
        
        // When
        vehicle.activate();
        
        // Then Activated Event must be fired
    }

    @Test( expected = IllegalStateException.class)
    public void activateActiveVehicle() throws Exception {

        // Given
        vehicle.applyEvent(new Activated());
        
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
        vehicle.applyEvent(new Activated());

        // When
        vehicle.changeLocation(1f, 1f);

        // Then ChangeLocationEvent must be fired
    }

}
