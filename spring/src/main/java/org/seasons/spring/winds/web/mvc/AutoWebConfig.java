package org.seasons.spring.winds.web.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class AutoWebConfig {


    @Bean(name = "dispatcherServlet")
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setDispatchOptionsRequest(true);
        dispatcherServlet.setDispatchTraceRequest(true);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        dispatcherServlet.setPublishEvents(true);
        dispatcherServlet.setEnableLoggingRequestDetails(true);
        return dispatcherServlet;
    }

    public static void main(String[] args) {


        AnnotationServletWebServerApplicationContext webApplicationContext = new AnnotationServletWebServerApplicationContext();

        webApplicationContext.register(AutoWebConfig.class);
        webApplicationContext.register(URLController.class);



        webApplicationContext.refresh();


    }



}
