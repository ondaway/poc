package com.ondaway.poc.cqrs;

import com.ondaway.poc.cqrs.bus.BusInMemory;
import com.ondaway.poc.vehicle.command.Activate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author ernesto
 */
public class BusInMemoryTest {

    BusInMemory bus;

    @Before
    public void setup() {
        this.bus = new BusInMemory();
    }

    @Test
    public void shouldFailIfCommandNotRegistered() throws Exception {

        // Background
        Activate command = new Activate(UUID.randomUUID(), UUID.randomUUID());
        
        // When
        bus.emit(command, (err) -> {
            //Then
            Assert.assertTrue(err.isPresent());
        }).get();
    }
    
    @Test
    public void shouldDispatchCommandToHandlerAsync() throws Exception {
        
        // Background
        Consumer<Command> handler = mock(Consumer.class);
        Activate command = new Activate(UUID.randomUUID(), UUID.randomUUID());
        
        // Given
        bus.registerHandler(Activate.class.getName(), handler);
        
        // When
        Optional<String> error = bus.emit(command).get(5, TimeUnit.SECONDS);
        
        // Then
        verify(handler, times(1)).accept(command);
        Assert.assertFalse(error.isPresent());
    }
    
}
