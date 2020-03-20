package org.raniaia.poseidon.framework.provide;

import java.lang.annotation.*;

/**
 * bean容器注入注解
 *
 * bean container inject annotation.
 *
 * Copyright: Create by tiansheng on 2019/12/9 17:31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Valid {

    String name() default "";

}
