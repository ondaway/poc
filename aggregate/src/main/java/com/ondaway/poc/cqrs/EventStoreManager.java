package com.ondaway.poc.cqrs;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Driver;
import static java.sql.DriverManager.println;
import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * System property eventstore.stores
 */
public class EventStoreManager {

    static {
        loadInitialDrivers();
        println("JDBC DriverManager initialized");
    }

    public static EventStore getStore() {
        ServiceLoader<EventStore> eventStoreLoader = ServiceLoader.load(EventStore.class);
//        eventStoreLoader.
        return null;
    }

    private static void loadInitialDrivers() {
        String stores;
        try {
            stores = AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getProperty("eventstore.stores"));
        } catch (Exception ex) {
            stores = null;
        }
        
        // If the driver is packaged as a Service Provider, load it.
        // Get all the drivers through the classloader
        // exposed as a java.sql.Driver.class service.
        // ServiceLoader.load() replaces the sun.misc.Providers()

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            ServiceLoader<EventStore> loadedStores = ServiceLoader.load(EventStore.class);
            Iterator<EventStore> driversIterator = loadedStores.iterator();
            
            /* Load these drivers, so that they can be instantiated.
            * It may be the case that the driver class may not be there
            * i.e. there may be a packaged driver with the service class
            * as implementation of java.sql.Driver but the actual class
            * may be missing. In that case a java.util.ServiceConfigurationError
            * will be thrown at runtime by the VM trying to locate
            * and load the service.
            *
            * Adding a try catch block to catch those runtime errors
            * if driver not available in classpath but it's
            * packaged as service and that service is there in classpath.
            */
            try {
                while (driversIterator.hasNext()) {
                    driversIterator.next();
                }
            } catch (Throwable t) {
                // Do nothing
            }
            return null;
        });

        println("EventStoreManager.initialize: eventstore.stores = " + stores);

        if (stores == null || stores.equals("")) {
            return;
        }
        String[] storesList = stores.split(":");
        println("number of Stores:" + storesList.length);
        for (String aStore : storesList) {
            try {
                println("EventStoreManager.Initialize: loading " + aStore);
                Class.forName(aStore, true, ClassLoader.getSystemClassLoader());
            } catch (Exception ex) {
                println("EventStoreManager.Initialize: load failed: " + ex);
            }
        }
    }
}
