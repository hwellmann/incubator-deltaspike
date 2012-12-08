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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.enterprise.inject.Alternative;

import org.apache.deltaspike.cache.api.Cache;
import org.apache.deltaspike.cache.api.CacheManager;

@Alternative
public class ConcurrentMapCacheManager implements CacheManager
{

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    private boolean dynamic = true;

    /**
     * Constructs a dynamic ConcurrentMapCacheManager, lazily creating cache instances as they are being requested.
     */
    public ConcurrentMapCacheManager()
    {
    }

    /**
     * Constructs a static ConcurrentMapCacheManager, managing caches for the specified cache names only.
     */
    public ConcurrentMapCacheManager(String... cacheNames)
    {
        setCacheNames(Arrays.asList(cacheNames));
    }

    /**
     * Specifies the set of cache names for this CacheManager's 'static' mode.
     * <p>
     * The number of caches and their names will be fixed after a call to this method, with no creation of further cache
     * regions at runtime.
     */
    public void setCacheNames(Collection<String> cacheNames)
    {
        if (cacheNames != null)
        {
            for (String name : cacheNames)
            {
                this.cacheMap.put(name, createConcurrentMapCache(name));
            }
            this.dynamic = false;
        }
    }

    public Collection<String> getCacheNames()
    {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    public Cache getCache(String name)
    {
        Cache cache = this.cacheMap.get(name);
        if (cache == null && this.dynamic)
        {
            synchronized (this.cacheMap)
            {
                cache = this.cacheMap.get(name);
                if (cache == null)
                {
                    cache = createConcurrentMapCache(name);
                    this.cacheMap.put(name, cache);
                }
            }
        }
        return cache;
    }

    /**
     * Create a new ConcurrentMapCache instance for the specified cache name.
     * 
     * @param name
     *            the name of the cache
     * @return the ConcurrentMapCache (or a decorator thereof)
     */
    protected Cache createConcurrentMapCache(String name)
    {
        return new ConcurrentMapCache(name);
    }

}
