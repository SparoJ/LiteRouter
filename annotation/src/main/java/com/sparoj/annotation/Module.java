package com.sparoj.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sdh on 2019/3/13
 */

@Retention(RetentionPolicy.CLASS)
public @interface Module {
    String moduleName();
    String providePackageName();
}
