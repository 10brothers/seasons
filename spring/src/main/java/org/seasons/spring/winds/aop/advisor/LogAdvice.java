package org.seasons.spring.winds.aop.advisor;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 用于输出日志的Advice，同时也是一个MethodInterceptor，一个Advice可以不是MethodInterceptor，
 * 但是一定要需要一个AdvisorAdapter将其适配成MethodInterceptor进行使用
 *
 * @author wangk
 * @date 2022/3/20
 */
public class LogAdvice implements Advice, MethodInterceptor {
    @Override
    public Object invoke (MethodInvocation invocation) throws Throwable {
        System.out.println("log "+ invocation.getMethod().getName()+" enter");
        Object proceed = invocation.proceed();
        System.out.println("log "+ invocation.getMethod().getName()+" out");
        return proceed;
    }
}
