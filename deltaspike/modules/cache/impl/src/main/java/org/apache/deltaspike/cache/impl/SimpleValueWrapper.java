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

import org.apache.deltaspike.cache.api.Cache.ValueWrapper;

public class SimpleValueWrapper implements ValueWrapper
{

    private final Object value;

    /**
     * Create a new SimpleValueWrapper instance for exposing the given value.
     * 
     * @param value
     *            the value to expose (may be <code>null</code>)
     */
    public SimpleValueWrapper(Object value)
    {
        this.value = value;
    }

    /**
     * Simply returns the value as given at construction time.
     */
    public Object get()
    {
        return this.value;
    }

}
