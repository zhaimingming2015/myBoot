package com.zhai.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhai.shiro.cache.RedisCacheManager;
import com.zhai.shiro.filter.RolesOrFilter;
import com.zhai.shiro.realm.CustomRealm;
import com.zhai.shiro.session.CustomSessionManager;
import com.zhai.shiro.session.RedisSessionDao;

/**
 * 
 * 授权两种方式：编程方式  和注解方式
 * @author ZHAIMINGMING
 *
 */

@Configuration
public class ShiroConfig {
	
	
	/**
	 * 2   重要 Bean
	 * 注入SecurityManager对象
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(CacheManager cacheShiroManager,SessionManager sessionManager){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(this.myShiroRealm());
		securityManager.setSessionManager(sessionManager);
		//使用自定义SessionManager
		//securityManager.setSessionManager(this.getCustomWebSessionManager());
		//使用redis缓存
		//securityManager.setCacheManager(this.getRedisCacheManager());
		//使用EHcache缓存
		securityManager.setCacheManager(cacheShiroManager);
		securityManager.setRememberMeManager(this.rememberMeManager());
		return securityManager;
	}
	
	/**
	 * 3   重要 Bean
	 * 注入自定义realm
	 */
	@Bean
	public CustomRealm myShiroRealm(){
		CustomRealm customRealm = new CustomRealm();
		customRealm.setCredentialsMatcher(this.matcher());
		return customRealm;
	}
	
	/**
	 * 1   重要 Bean
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		//默认登录页url
		shiroFilter.setLoginUrl("/login");
		//未认证的url
		shiroFilter.setUnauthorizedUrl("/error");
		
		//自定义shiro过滤器
        Map<String, Filter> myFilters = new HashMap<String, Filter>();
		myFilters.put("rolesOr", new RolesOrFilter());
		shiroFilter.setFilters(myFilters);
		
		
		Map<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("/static/**", "anon");
        hashMap.put("/login", "anon");
        hashMap.put("/add", "anon");
        hashMap.put("/subLogin", "anon");
        hashMap.put("/kaptcha", "anon");
        hashMap.put("/redis", "anon");
        /*hashMap.put("/testRole",  "roles[admin]");
        hashMap.put("/testRole1", "roles[admin,user]");
        hashMap.put("/testPerms",  "perms[user:select]");
        hashMap.put("/testPerms1", "perms[user:select,user:add]");*/
        //hashMap.put("/testRole",  "roles[admin,user]");
        hashMap.put("/testRole", "rolesOr[admin,user]");
        
        
        hashMap.put("/*", "authc");
        shiroFilter.setFilterChainDefinitionMap(hashMap);//过滤器链
        
        return shiroFilter;
	}
	
	/**
	 * 4   重要 Bean
	 * 加密管理器
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher matcher(){
		HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");
		matcher.setHashIterations(1);//设置加密次数
		return matcher;
	}
	
	/**
	 * 6   重要 Bean
	 * Shiro生命周期处理器
	 * 
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		 return new LifecycleBeanPostProcessor();
	}

	
	/**
	 * 5   重要 Bean
	 * 
	 * 启用shrio授权注解方式，AOP式方法级权限检查
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
		
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		
		return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * 6   重要 Bean
	 * session会话管理
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager defaultWebSessionManager(CacheManager cacheShiroManager){
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		//sessionManager.setSessionDAO(this.redisSessionDao());
		sessionManager.setCacheManager(cacheShiroManager);
		
		return sessionManager;
	}
	
	/**
	 * 自定义SessionManager
	 * @return
	 */
	/*@Bean
	public CustomSessionManager getCustomWebSessionManager(){
		CustomSessionManager customSessionManager=new CustomSessionManager();
		customSessionManager.setSessionDAO(this.redisSessionDao());
		return customSessionManager;
	}*/
	
	
	/**
	 * RedisSessionDao
	 * @return
	 */
	/*@Bean
	public RedisSessionDao redisSessionDao(){
		return new RedisSessionDao();
	}
	
	@Bean
	public RedisCacheManager getRedisCacheManager(){
		return new RedisCacheManager();
	}*/
	
	/**
	 * 缓存管理器 使用EHcache
	 */
	@Bean
	public CacheManager getCacheShiroManager(/*EhCacheManagerFactoryBean factoryBean*/){
		EhCacheManager ehCacheManager=new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		//ehCacheManager.setCacheManager(factoryBean.getObject());
		return ehCacheManager;
		
	}
	
	
	/**
	 * 自定义shiro过滤器
	 * @return
	 */
	@Bean
	public RolesOrFilter myRolesOrFilter(){
		
		return new RolesOrFilter();
	}
	
	/**
	 * rememberMe管理器
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(){
		CookieRememberMeManager rememberMeManager=new CookieRememberMeManager();
		rememberMeManager.setCookie(this.cookie());
		return rememberMeManager;
	}
	
	/**
	 * 记住密码Cookie
	 * @return
	 */
	@Bean
	public SimpleCookie cookie(){
		SimpleCookie cookie=new SimpleCookie("rememberMe");
		cookie.setMaxAge(7*24*3600);
		return cookie;
	}
}
