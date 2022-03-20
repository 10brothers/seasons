package org.seasons.spring.winds.aop.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * 基于类注解和方法注解的PointcutAdvisor
 *
 * @author wangk
 * @date 2022/3/20
 */
public class AnnotationPointcutAdvisor extends AbstractPointcutAdvisor {

    private final Advice advice = new LogAdvice();

    private final Pointcut pointcut = new AnnotationMatchingPointcut(AOPClass.class, AOPMethod.class);

    @Override
    public Pointcut getPointcut () {
        return pointcut;
    }

    @Override
    public Advice getAdvice () {
        return advice;
    }
}
