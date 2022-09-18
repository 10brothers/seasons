package org.seasons.spring.winds.web.mvc;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.support.ServletContextAwareProcessor;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 参考SpringBoot中的处理，使用嵌入式的tomcat。
 * 要处理的点在于：Tomcat启动过程需要添加DispatcherServlet，DispatcherServlet由Spring的ApplicationContext创建管理，也就是说可能需要在refresh之后才能启动Tomcat。
 * 但是WebApplicationContext启动过程中，需要ServletContext才能启动，否则失败。
 * 基于上面这个矛盾，可以使用@Configuration注解和@Bean注解以及ApplicationContext中的onRefresh扩展点来解决这个矛盾。
 * 因为@Configuration的注解处理在onReresh方法之前，此时ApplicationContext中已经存在了DispatcherServlet这个Bean，
 * 而同时在创建并启动Tomcat后，就能获得ServletContext实例，保存在WebApplicationContext。且这个时机是早于使用ServletContext的时机（resourceHandlerMapping的创建）
 *
 * 参考SpringBoot的 AnnotationConfigServletWebServerApplicationContext
 */
public class AnnotationServletWebServerApplicationContext extends GenericWebApplicationContext implements AnnotationConfigRegistry {

    private final AnnotatedBeanDefinitionReader reader;

    private final ClassPathBeanDefinitionScanner scanner;


    private final Set<Class<?>> annotatedClasses = new LinkedHashSet<>();

    private String[] basePackages;

    private ServletConfig servletConfig;

    public AnnotationServletWebServerApplicationContext() {
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }


    @Override
    protected void onRefresh() {
        super.onRefresh();

        Tomcat tomcat = new Tomcat();
        File baseDir = (createTempDir("tomcat"));
        assert baseDir != null;
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setThrowOnFailure(true);
        connector.setPort(9999);
        tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);

        StandardContext standardContext = new StandardContext();
        standardContext.setName("");
        standardContext.setPath("");
        standardContext.addLifecycleListener(new Tomcat.FixContextListener());
        AtomicReference<ServletContext> ar = new AtomicReference<>();
        standardContext.addServletContainerInitializer((set, servletContext) -> {
            ar.set(servletContext);
            servletContext.addServlet("dispatcherServlet", this.getBean("dispatcherServlet", DispatcherServlet.class)).addMapping("/");
        }, Collections.emptySet());
        tomcat.getHost().addChild(standardContext);
        try {
            tomcat.start();
            // 开启一个阻塞的非守护线程，避免tomcat关闭
            Thread awaitThread = new Thread("container-1") {

                @Override
                public void run() {
                    tomcat.getServer().await();
                }

            };
            awaitThread.setContextClassLoader(AutoWebConfig.class.getClassLoader());
            awaitThread.setDaemon(false);
            awaitThread.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
        this.setServletContext(ar.get());
    }


    protected static File createTempDir(String prefix) {
        try {
            File tempDir = File.createTempFile(prefix + ".", ".8080");
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException ex) {
            //
        }
        return null;
    }

    @Override
    public void register(Class<?>... componentClasses) {
        Assert.notEmpty(componentClasses, "At least one annotated class must be specified");
        this.annotatedClasses.addAll(Arrays.asList(componentClasses));
    }

    @Override
    public void scan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        this.basePackages = basePackages;
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new WebApplicationContextServletContextAwareProcessor(this));
        beanFactory.ignoreDependencyInterface(ServletContextAwareProcessor.class);
        if (this.basePackages != null && this.basePackages.length > 0) {
            this.scanner.scan(this.basePackages);
        }
        if (!this.annotatedClasses.isEmpty()) {
            this.reader.register(ClassUtils.toClassArray(this.annotatedClasses));
        }
    }

    // GenericWebApplicationContext不处理这个，需要子类自己处理
    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }
}
