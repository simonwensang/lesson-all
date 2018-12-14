package com.simon.lesson3.annotation;

import com.simon.lesson3.common.RequestMethod;

import java.lang.annotation.*;

/**
 * Created by sang on 2018/12/14.
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String name() default "";

    String[] value() default {};

    RequestMethod[] method() default {};

}
