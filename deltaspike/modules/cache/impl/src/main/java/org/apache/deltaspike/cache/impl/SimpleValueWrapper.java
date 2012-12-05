package org.apache.deltaspike.cache.impl;

import org.apache.deltaspike.cache.api.Cache.ValueWrapper;

public class SimpleValueWrapper implements ValueWrapper {

    private final Object value;


    /**
     * Create a new SimpleValueWrapper instance for exposing the given value.
     * 
     * @param value the value to expose (may be <code>null</code>)
     */
    public SimpleValueWrapper(Object value) {
        this.value = value;
    }


    /**
     * Simply returns the value as given at construction time.
     */
    public Object get() {
        return this.value;
    }

}
