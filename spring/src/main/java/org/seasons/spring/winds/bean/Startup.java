package org.seasons.spring.winds.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/3/20
 */
public class Startup {

    public static void main (String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(ABean.class);
        context.register(BBean.class);
        context.refresh();

    }
}
