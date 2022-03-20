package org.seasons.spring.winds.aop.advisor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pointcut中的ClassFilter用于识别的类注解
 *
 * @author wangk
 * @date 2022/3/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AOPClass {
}
