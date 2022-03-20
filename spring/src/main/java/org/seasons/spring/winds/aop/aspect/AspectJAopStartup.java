package org.seasons.spring.winds.aop.aspect;

import org.seasons.spring.winds.aop.XxOoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于注解的AspectJ AOP代理 DEMO
 *
 * @author wangk
 * @date 2022/3/12
 */
public class AspectJAopStartup {
    public static void main (String[] args) {
        AnnotationConfigApplicationContext aca = new AnnotationConfigApplicationContext();

        aca.register(AspectConfiguration.class);

        aca.refresh();

        aca.getBean(XxOoService.class).doOo();
        System.out.println( );

    }
}
