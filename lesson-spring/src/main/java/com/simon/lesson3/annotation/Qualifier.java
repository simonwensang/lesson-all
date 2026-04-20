package com.simon.lesson3.annotation;

import java.lang.annotation.*;

/**
 * Created by sang on 2018/12/14.
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qualifier {

    String value() default "" ;

}
