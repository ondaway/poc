package com.ondaway.poc.cqrs;

import com.ondaway.poc.cqrs.inmemory.BusInMemory;
import com.ondaway.poc.vehicle.command.Activate;
import java.util.UUID;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author ernesto
 */
public class BusInMemoryTest {

    Bus bus;

    @Before
    public void setup() {
        this.bus = new BusInMemory();
    }
    
    @Test
    public void shouldSend() {
        
        // Given
        Consumer<Command> handler = mock(Consumer.class);
        Activate command = new Activate(UUID.randomUUID(), UUID.randomUUID());
        bus.registerHandler(Activate.class.getName(), handler);
        
        // When
        bus.send(command);
        
        // Then
        verify(handler, times(1)).accept(command);
    }
    
}
