package org.seasons.spring.winds;

import org.seasons.spring.winds.aop.aspect.AspectConfiguration;
import org.seasons.spring.winds.aop.XxOoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/2/22
 */
public class Application {
    public static void main (String[] args) {
        AnnotationConfigApplicationContext aca = new AnnotationConfigApplicationContext();

        aca.register(AspectConfiguration.class);

        aca.refresh();

        aca.getBean(XxOoService.class).doXx();
        System.out.println( );

    }
}
