package com.ondaway.poc.ddd;

import java.util.UUID;

/**
 *
 * @author jeroldan
 * @param <T>
 */
public interface Repository<T extends AggregateRoot> {
    
    void Save(AggregateRoot aggregate);
 
    T GetById(UUID id, T instance);
 
}
