package org.seasons.spring.winds.aop.aspect;

import org.seasons.spring.winds.aop.XxOoService;
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
public class AspectConfiguration {

    @Bean
    public XxOoService birdService () {
        return new XxOoService();
    }

    @Bean
    public BirdAdvisor pointCu() {
        return new BirdAdvisor();
    }

}
