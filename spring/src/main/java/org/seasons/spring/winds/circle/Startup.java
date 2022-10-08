package org.seasons.spring.winds.circle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Startup {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acc = new AnnotationConfigApplicationContext();
//        acc.scan("org.seasons.spring.winds.circle");
        acc.register(CircleRefA.class);
        acc.register(CircleRefB.class);

        acc.refresh();
        System.out.println(acc.getBean(CircleRefA.class));
        System.out.println(acc.getBean(CircleRefB.class));

    }
}
