package org.seasons.spring.winds.web.flux;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
@Configuration
@EnableWebFlux
public class WebFluxConfig {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(WebFluxConfig.class);
        applicationContext.refresh();

        // HttpHandler 等同于MVC的DispatchServlet，负责路由请求到具体的控制器
        HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(applicationContext).build();
        // adapter的目的是为了适配不同的服务器实现，比如netty jetty undertow tomcat等
        ReactorHttpHandlerAdapter reactorHttpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 不同的server有不同的创建方式
        DisposableServer server = HttpServer.create().port(11024).handle(reactorHttpHandlerAdapter).bindNow();
        server.onDispose().block();

        // SpringBoot 的实现

        // 通过@Bean来提前创建好 HttpHandler
        // 通过重写onRefresh方法，来配置WebServerManager
        // 通过注册一个Lifecycle，在其onRefresh事件中启动WebServer


        // 请求处理入口 以netty实现为例
        // ChannelOperationsHandler


        // reactive的编程模式

        // publisher subscriber

    }

}
