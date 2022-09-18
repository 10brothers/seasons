/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.seasons.spring.winds.web.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.util.Assert;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.ServletContextAwareProcessor;

/**
 *  继承ServletContextAwareProcessor，实际上是为了覆盖原有的ServletContextAwareProcessor，目的是为了可以更早的获取到ServletContext。
 *  原先的模式是先启动Servlet容器，然后再去启动SpringFramework，而使用内嵌Tomcat，是先启动SpringFramework再启动Servlet容器。这样就要求
 *  在需要使用到ServletContext的地方，必须已经准备好了ServletContext。Servlet容器的启动时机在onRefresh时，此时已经执行过了向BeanFactory中添加BeanPostProcessor的动作，
 *  而因为servletContext属性为null，导致未能添加ServletContextAwareProcessor到BeanFactory，最终也就导致基于ServletContextAware的Bean，无法获取到ServletContext实例。
 *  通过扩展ServletContextAwareProcessor，实现优先从WebApplicationContext中获取ServletContext实例，而在启动Servlet容器时，会将ServletContext保存到WebApplicationContext中。
 *  最终解决问题。
 *
 *  这里用到 resourceHandlerMapping
 */
public class WebApplicationContextServletContextAwareProcessor extends ServletContextAwareProcessor {

	private final ConfigurableWebApplicationContext webApplicationContext;

	public WebApplicationContextServletContextAwareProcessor(ConfigurableWebApplicationContext webApplicationContext) {
		Assert.notNull(webApplicationContext, "WebApplicationContext must not be null");
		this.webApplicationContext = webApplicationContext;
	}

	@Override
	protected ServletContext getServletContext() {
		ServletContext servletContext = this.webApplicationContext.getServletContext();
		return (servletContext != null) ? servletContext : super.getServletContext();
	}

	@Override
	protected ServletConfig getServletConfig() {
		ServletConfig servletConfig = this.webApplicationContext.getServletConfig();
		return (servletConfig != null) ? servletConfig : super.getServletConfig();
	}

}
