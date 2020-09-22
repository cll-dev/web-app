package com.yitongyin.modules.ad.inti;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContextSupport implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> type) {
		return applicationContext.getBean(type);
	}

	public static void register(Class<?> type) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(type);
		// beanDefinitionBuilder.addPropertyValue(type.getCanonicalName(), );
		beanDefinitionBuilder.setScope("prototype");
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
				.getBeanFactory();
		beanDefinitonRegistry.registerBeanDefinition(type.getCanonicalName(),
				beanDefinitionBuilder.getRawBeanDefinition());
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextSupport.applicationContext = applicationContext;
	}

	public static ApplicationContext getContext() {
		return SpringContextSupport.applicationContext;
	}
}