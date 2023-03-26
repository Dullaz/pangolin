package com.hasan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    /**
     * (Optional) The name of the column. Defaults to field name.
     */
    String name() default "";

    /**
     * (Optional) Used for storing date formats and custom enum functions to aid in deserialization.
     *
     */
    String mutation() default "";

}
