package com.ondaway.poc.cqrs;

import com.ondaway.poc.cqrs.bus.BusInMemory;
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

    BusInMemory bus;

    @Before
    public void setup() {
        this.bus = new BusInMemory();
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldFailIfCommandNotRegistered() throws Exception {
        Activate command = new Activate(UUID.randomUUID(), UUID.randomUUID());
        bus.emit(command);
    }
    
    
    public void shouldDispatchCommandToHandler() throws Exception { 
        
        // Background
        Consumer<Command> handler = mock(Consumer.class);
        Activate command = new Activate(UUID.randomUUID(), UUID.randomUUID());
        
        // Given
        bus.registerHandler(Activate.class.getName(), handler);
        
        // When
        bus.emit(command);
        
        // Then
        verify(handler, times(1)).accept(command);
    }
    
}
