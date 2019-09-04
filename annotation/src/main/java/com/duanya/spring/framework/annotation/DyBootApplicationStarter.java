package com.duanya.spring.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DyBootApplicationStarter {
    String[] scannerPath();
}
