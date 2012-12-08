/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.deltaspike.cache.impl;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.deltaspike.cache.api.Cache;

public class ConcurrentMapCache implements Cache
{

    private static final Object NULL_HOLDER = new NullHolder();

    private final String name;

    private final ConcurrentMap<Object, Object> store;

    private final boolean allowNullValues;

    /**
     * Creates a new ConcurrentMapCache with the specified name.
     * 
     * @param name
     *            the name of the cache
     */
    public ConcurrentMapCache(String name)
    {
        this(name, new ConcurrentHashMap<Object, Object>(), true);
    }

    /**
     * Creates a new ConcurrentMapCache with the specified name.
     * 
     * @param name
     *            the name of the cache
     * @param allowNullValues
     *            whether to accept and convert null values for this cache
     */
    public ConcurrentMapCache(String name, boolean allowNullValues)
    {
        this(name, new ConcurrentHashMap<Object, Object>(), allowNullValues);
    }

    /**
     * Create a new ConcurrentMapCache with the specified name and the given internal ConcurrentMap to use.
     * 
     * @param name
     *            the name of the cache
     * @param store
     *            the ConcurrentMap to use as an internal store
     * @param allowNullValues
     *            whether to allow <code>null</code> values (adapting them to an internal null holder value)
     */
    public ConcurrentMapCache(String name, ConcurrentMap<Object, Object> store,
            boolean allowNullValues)
    {
        this.name = name;
        this.store = store;
        this.allowNullValues = allowNullValues;
    }

    public String getName()
    {
        return this.name;
    }

    public ConcurrentMap<?, ?> getNativeCache()
    {
        return store;
    }

    public boolean isAllowNullValues()
    {
        return allowNullValues;
    }

    public ValueWrapper get(Object key)
    {
        Object value = store.get(key);
        return (value != null ? new SimpleValueWrapper(fromStoreValue(value)) : null);
    }

    public void put(Object key, Object value)
    {
        store.put(key, toStoreValue(value));
    }

    public void evict(Object key)
    {
        store.remove(key);
    }

    public void clear()
    {
        store.clear();
    }

    /**
     * Converts the given value from the internal store to a user value returned from the get method (adapting
     * <code>null</code>).
     * 
     * @param storeValue
     *            the store value
     * @return the value to return to the user
     */
    protected Object fromStoreValue(Object storeValue)
    {
        if (allowNullValues && storeValue == NULL_HOLDER)
        {
            return null;
        }
        return storeValue;
    }

    /**
     * Converts the given user value, as passed into the put method, to a value in the internal store (adapting
     * <code>null</code>).
     * 
     * @param userValue
     *            the given user value
     * @return the value to store
     */
    protected Object toStoreValue(Object userValue)
    {
        if (allowNullValues && userValue == null)
        {
            return NULL_HOLDER;
        }
        return userValue;
    }

    @SuppressWarnings("serial")
    private static class NullHolder implements Serializable
    {
    }

}