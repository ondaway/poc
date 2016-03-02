package com.ondaway.poc.ddd;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author jeroldan
 * @param <T>
 */
public interface Repository<T extends AggregateRoot> {
    
    void save(AggregateRoot aggregate);
 
    Optional<T> getById(UUID id, Class clazz);
 
}
