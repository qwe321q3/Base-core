
package com.tydic.base.core.web.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 保存Spring容器信息，用于获取容器中的bean
 * @author tianshuo
 * @version Id: SpringContextUtils.java, v 0.1 2016/7/28 0028 15:28 tianshuo Exp $$
 */
public class SpringContextUtils implements ApplicationContextAware, ServletContextListener {

	private ApplicationContext applicationContext;

	private ServletContext servletContext;

	private final static SpringContextUtils SELF_INSTANCE = new SpringContextUtils();

	public static SpringContextUtils getInstance() {
		return SELF_INSTANCE;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		getInstance().applicationContext = applicationContext;
	}

	public static void setApplicationContext(ServletContext servletContext){
		ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		getInstance().applicationContext = springContext;
	}

	public static ApplicationContext getApplicationContext() {
		return getInstance().applicationContext;
	}

	public static Object getBean(String name) throws BeansException {
		return getApplicationContext().getBean(name);
	}

	public static Object getBean(String name, Class requiredType) throws BeansException {
		return getInstance().applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return getInstance().applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return getInstance().applicationContext.isSingleton(name);
	}

	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return getInstance().applicationContext.getType(name);
	}

	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return getInstance().applicationContext.getAliases(name);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		this.servletContext.removeAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		getInstance().servletContext = null;
	}

	public void contextInitialized(ServletContextEvent sce) {
		this.servletContext = sce.getServletContext();
		getInstance().applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
	}
}