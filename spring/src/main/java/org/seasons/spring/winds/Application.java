package org.seasons.spring.winds;

import org.seasons.spring.winds.aop.AopConfiguration;
import org.seasons.spring.winds.aop.BirdService;
import org.seasons.spring.winds.model.Bird;
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

        aca.register(AopConfiguration.class);

        aca.refresh();

        aca.getBean(BirdService.class).feed();
        System.out.println( );

    }
}
