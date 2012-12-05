package org.apache.deltaspike.cache.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.deltaspike.cache.api.Cache;
import org.apache.deltaspike.cache.api.CacheManager;
import org.apache.deltaspike.cache.api.Cacheable;
import org.apache.deltaspike.cache.api.Cache.ValueWrapper;


@Cacheable
@Interceptor
public class CacheableInterceptor {

    @Inject
    private CacheManager cacheManager;

    @AroundInvoke
    public Object retrieve(InvocationContext ic) throws Exception {
        Method method = ic.getMethod();
        Cacheable annotation = method.getAnnotation(Cacheable.class);
        String cacheName = annotation.value();
        Cache cache = cacheManager.getCache(cacheName);
        List<Object> key = toKey(ic);
        ValueWrapper wrapper = cache.get(key);
        Object result = null;
        if (wrapper == null) {
            result = ic.proceed();
            cache.put(key, result);
        }
        else {
            result = wrapper.get();
        }
        return result;
    }

    private List<Object> toKey(InvocationContext ic) {
        List<Object> key = new ArrayList<Object>();
        key.add(ic.getTarget());
        key.add(ic.getMethod());
        for (Object param : ic.getParameters()) {
            key.add(param);
        }
        return key;
    }
}
