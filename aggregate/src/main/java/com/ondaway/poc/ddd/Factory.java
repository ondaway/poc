package com.ondaway.poc.ddd;

/**
 *
 * @author jeroldan
 * @param <T>
 */
public class Factory<T extends AggregateRoot> {

    private final Class<T> clazz;

    public static <T extends AggregateRoot> Factory<T> createFactory(final Class<T> clazz) {
        return new Factory<T>(clazz);
    }

    private Factory(final Class<T> clazz) {
        this.clazz = clazz;
    }
    
    public T create() throws InstantiationException, IllegalAccessException {
        T aggregate = clazz.newInstance();
        return aggregate;
    }

}
