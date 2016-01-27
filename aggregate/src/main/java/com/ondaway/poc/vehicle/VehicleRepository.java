package com.ondaway.poc.vehicle;

import com.ondaway.poc.cqrs.EventRepository;
import com.ondaway.poc.cqrs.EventStore;

/**
 *
 * @author jeroldan
 */
public class VehicleRepository extends EventRepository<Vehicle>{

    public VehicleRepository(EventStore store) {
        super(store);
    }
    
}
