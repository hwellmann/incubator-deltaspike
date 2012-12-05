package org.apache.deltaspike.cache.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Inherited
@Documented
public @interface Cacheable {

    /**
     * Name of the caches in which the update takes place.
     * <p>
     * May be used to determine the target cache (or caches), matching the qualifier value (or the
     * bean name(s)) of (a) specific bean definition.
     */
    @Nonbinding
    String value() default "";

    /**
     * Spring Expression Language (SpEL) attribute for computing the key dynamically.
     * <p>
     * Default is "", meaning all method parameters are considered as a key.
     */
    @Nonbinding
    String key() default "";

}
