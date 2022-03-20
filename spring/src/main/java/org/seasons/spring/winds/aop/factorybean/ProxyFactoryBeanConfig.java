package org.seasons.spring.winds.aop.factorybean;

import org.seasons.spring.winds.aop.XxOoService;
import org.seasons.spring.winds.aop.advisor.AnnotationPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/3/20
 */
@Configuration
public class ProxyFactoryBeanConfig {

    @Bean
    public ProxyFactoryBean factoryBean () {
        ProxyFactoryBean bean = new ProxyFactoryBean();
        bean.setTarget(new XxOoService());
        bean.addAdvisor(new AnnotationPointcutAdvisor());
        return bean;
    }


}
