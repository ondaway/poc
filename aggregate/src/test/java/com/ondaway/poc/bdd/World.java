package com.ondaway.poc.bdd;

import com.ondaway.poc.ddd.AggregateRoot;
import com.ondaway.poc.ddd.Repository;
import java.util.UUID;

/**
 *
 * @author ernesto
 */
public class World<T extends AggregateRoot> {

    
    Repository<T> repository;
    
    public World(Repository<T> repository) {
        this.repository = repository;
    }
    
    AggregateRoot find(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void save(AggregateRoot subject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
