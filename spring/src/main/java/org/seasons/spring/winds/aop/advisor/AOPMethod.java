package org.seasons.spring.winds.aop.advisor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于注解的Pointcut MethodMatcher匹配识别的注解
 *
 * @author wangk
 * @date 2022/3/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AOPMethod {
}
