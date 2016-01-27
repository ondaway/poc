package com.ondaway.poc.ddd;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author jeroldan
 * @param <T>
 */
public interface Repository<T extends AggregateRoot> {
    
    void Save(AggregateRoot aggregate);
 
    Optional<T> GetById(UUID id, Class clazz);
 
}
