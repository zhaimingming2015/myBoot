package com.zhai.config;

import java.io.IOException;
import java.util.Properties;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.zhai.beetl.BeetlConfiguration;


@Configuration
public class BeetlConfig {
	
	
	@Bean(initMethod="init")
	public BeetlConfiguration beetlConfiguration() {
		
		//BeetlGroupUtilConfiguration configuration=new BeetlGroupUtilConfiguration();
		BeetlConfiguration configuration=new BeetlConfiguration();
		
		ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
		
		String path=null;
		try {
			path = patternResolver.getResource("classpath:/").getFile().getPath();
			System.out.println("==================path:"+path);
			WebAppResourceLoader resourceLoader=new WebAppResourceLoader(path);
			
			configuration.setResourceLoader(resourceLoader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(BeetlConfig.class.getClassLoader());
		//configuration.setResourceLoader(resourceLoader);
		Properties properties = new Properties();
		properties.setProperty("DELIMITER_STATEMENT_START", "@");
		properties.setProperty("DELIMITER_STATEMENT_END", "null");
		configuration.setConfigProperties(properties);
		return configuration;
	}

	@Bean
	public BeetlSpringViewResolver getBeetlSpringViewResolver(){
		BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		beetlSpringViewResolver.setConfig(this.beetlConfiguration());
		beetlSpringViewResolver.setPrefix("WEB-INF/views/");
		beetlSpringViewResolver.setSuffix(".html");
		beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		beetlSpringViewResolver.setOrder(0);
		
		
		return beetlSpringViewResolver;
	}
	
}
