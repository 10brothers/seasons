package org.seasons.spring.winds.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/3/9
 */
@Aspect
public class BirdAdvisor {

    @Pointcut("execution(public * org.seasons.spring.winds.aop.BirdService.*(..))")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void before() {
        System.out.println("before");
    }



}
