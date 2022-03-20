package org.seasons.spring.winds.aop.independency;

import org.seasons.spring.winds.aop.XxOoService;
import org.seasons.spring.winds.aop.advisor.AnnotationPointcutAdvisor;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.core.DebuggingClassWriter;

/**
 * 使用SpringAOP包实现的编程式AOP代理 DEMO
 *
 * @author wangk
 * @date 2022/3/12
 */
public class ProgrammaticProxy {

    public static void main (String[] args) {
        // 可以输出cglib代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./target/cglib/proxy");
        // 创建代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        // AOP代理配置管理器
        AdvisedSupport support = new AdvisedSupport();
        // 设置被代理目标实例
        support.setTarget(new XxOoService());
        // 添加advisor，Advisor中Advice要么同时实现了MethodInterceptor（或者IntroductionInterceptor），
        // 要么有对应的AdvisorAdapter，否则报错
        support.addAdvisor(new AnnotationPointcutAdvisor());
        // 获取一个AopProxyFactory，默认是DefaultAopProxyFactory
        AopProxyFactory aopProxyFactory = proxyFactory.getAopProxyFactory();
        // 创建一个AopProxy，有Jdk和Cglib两种实现，此种情况使用的cglib
        // 这一步是根据AOP代理配置来选择使用哪种代理方
        AopProxy aopProxy = aopProxyFactory.createAopProxy(support);
        // 创建代理对象
        XxOoService proxy =(XxOoService) aopProxy.getProxy();
        // 通过代理对象调用方法
        proxy.doXx();
    }
}
