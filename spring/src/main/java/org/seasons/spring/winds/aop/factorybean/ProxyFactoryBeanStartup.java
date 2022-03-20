package org.seasons.spring.winds.aop.factorybean;

import org.seasons.spring.winds.aop.XxOoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/3/20
 */
public class ProxyFactoryBeanStartup {

    public static void main (String[] args) {

        AnnotationConfigApplicationContext aca = new AnnotationConfigApplicationContext();
        aca.register(ProxyFactoryBeanConfig.class);
        aca.refresh();

        aca.getBean(XxOoService.class).doXx();

    }

}
