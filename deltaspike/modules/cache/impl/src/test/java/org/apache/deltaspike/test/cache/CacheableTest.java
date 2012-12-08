package org.apache.deltaspike.test.cache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.apache.deltaspike.test.utils.ShrinkWrapArchiveUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CacheableTest
{
    @Inject
    private Calculator client;

    @Deployment
    public static WebArchive deploy()
    {
        JavaArchive[] archives = ShrinkWrapArchiveUtil.getArchives(
                null,
                "META-INF/beans.xml",
                null,
                null);

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(archives)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    
    
    @Test
    public void addition()     
    {
        int result1 = client.add(2, 3);
        assertThat(result1, is(5));
        assertThat(CalculatorImpl.getNumInvocations(), is(1));

        int result2 = client.add(2, 3);
        assertThat(result2, is(5));
        assertThat(CalculatorImpl.getNumInvocations(), is(1));

        int result3 = client.add(2, 6);
        assertThat(result3, is(8));
        assertThat(CalculatorImpl.getNumInvocations(), is(2));
    }
}
