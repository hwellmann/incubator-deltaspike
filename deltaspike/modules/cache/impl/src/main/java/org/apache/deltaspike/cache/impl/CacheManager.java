package org.apache.deltaspike.cache.impl;

import java.util.Collection;

import org.apache.deltaspike.cache.api.Cache;

public interface CacheManager {

    /**
     * Return the cache associated with the given name.
     * 
     * @param name cache identifier (must not be {@code null})
     * @return associated cache, or {@code null} if none is found
     */
    Cache getCache(String name);

    /**
     * Return a collection of the caches known by this cache manager.
     * 
     * @return names of caches known by the cache manager.
     */
    Collection<String> getCacheNames();
}
