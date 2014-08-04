package com.aokunsang.authority;

/**
 * Created by marty on 14-8-4.
 */
import com.aokunsang.util.ResultTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FireAuthority {
    AuthorityType[] authorityTypes();
    ResultTypeEnum resultType() default ResultTypeEnum.page;
}
