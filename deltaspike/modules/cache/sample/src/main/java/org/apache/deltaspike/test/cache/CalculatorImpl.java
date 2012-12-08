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
package org.apache.deltaspike.test.cache;

import javax.enterprise.context.ApplicationScoped;

import org.apache.deltaspike.cache.api.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CalculatorImpl implements Calculator
{
    private static Logger log = LoggerFactory.getLogger(CalculatorImpl.class);
    
    private static int numInvocations;

    @Override
    @Cacheable("calc")
    public int add(int op1, int op2)
    {
        numInvocations++;
        log.info("CalculatorImpl.add(), numInvocations = {}", numInvocations);
        return op1 + op2;
    }
    
    public static int getNumInvocations() 
    {
        return numInvocations;
    }

    public static void setNumInvocations(int value) 
    {
        numInvocations = value;
    }
}
