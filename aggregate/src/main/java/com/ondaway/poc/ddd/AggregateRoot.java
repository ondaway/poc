package com.ondaway.poc.ddd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;

import org.reflections.scanners.MethodParameterScanner;

/**
 *
 * @author ernesto
 */
public class AggregateRoot {
    
    public UUID id = UUID.randomUUID();
    
    private final List<Event> pendingEvents;

    public AggregateRoot() {
        this.pendingEvents = new ArrayList();
    }
    
    public void mutate(Event event) {
        Reflections reflections = new Reflections("com.ondaway.poc", new MethodParameterScanner());
        Set<Method> methods = reflections.getMethodsMatchParams(event.getClass());
        methods.stream().forEach( method -> {
            try {
                method.invoke(this, event);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                //TODO:  throw new ....???
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        });
        pendingEvents.add(event);
    }
    
    public List<Event> getPendingEvents() {
        return pendingEvents;
    }
    
    public void clearPendingEvents() {
        this.pendingEvents.clear();
    }
}
