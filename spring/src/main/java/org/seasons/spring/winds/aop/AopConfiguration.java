package org.seasons.spring.winds.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/3/9
 */
@EnableAspectJAutoProxy
@Configuration
public class AopConfiguration {

    @Bean
    public BirdService birdService () {
        return new BirdService();
    }

    @Bean
    public BirdAdvisor pointCu() {
        return new BirdAdvisor();
    }

}
