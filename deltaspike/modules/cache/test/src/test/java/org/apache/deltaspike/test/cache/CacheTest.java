package org.apache.deltaspike.test.cache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;

@RunWith(PaxExam.class)
public class CacheTest
{
    @Inject
    private Calculator calculator;

    @Test
    public void addition()     
    {
        int result1 = calculator.add(2, 3);
        assertThat(result1, is(5));
        assertThat(CalculatorImpl.getNumInvocations(), is(1));

        int result2 = calculator.add(2, 3);
        assertThat(result2, is(5));
        assertThat(CalculatorImpl.getNumInvocations(), is(1));

        int result3 = calculator.add(2, 6);
        assertThat(result3, is(8));
        assertThat(CalculatorImpl.getNumInvocations(), is(2));
    }
}
