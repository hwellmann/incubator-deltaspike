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
public class CacheableInterceptor
{

    @Inject
    private CacheManager cacheManager;

    @AroundInvoke
    public Object retrieve(InvocationContext ic) throws Exception
    {
        Method method = ic.getMethod();
        Cacheable annotation = method.getAnnotation(Cacheable.class);
        String cacheName = annotation.value();
        Cache cache = cacheManager.getCache(cacheName);
        List<Object> key = toKey(ic);
        ValueWrapper wrapper = cache.get(key);
        Object result = null;
        if (wrapper == null)
        {
            result = ic.proceed();
            cache.put(key, result);
        }
        else
        {
            result = wrapper.get();
        }
        return result;
    }

    private List<Object> toKey(InvocationContext ic)
    {
        List<Object> key = new ArrayList<Object>();
        key.add(ic.getTarget());
        key.add(ic.getMethod());
        for (Object param : ic.getParameters())
        {
            key.add(param);
        }
        return key;
    }
}
