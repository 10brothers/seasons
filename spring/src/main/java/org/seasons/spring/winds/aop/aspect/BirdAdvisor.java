package org.seasons.spring.winds.aop.aspect;

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

    @Pointcut("execution(public * org.seasons.spring.winds.aop.XxOoService.doXx(..))")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void before() {
        System.out.println("aspectJ before");
    }



}
