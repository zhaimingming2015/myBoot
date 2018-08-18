package com.zhai.test.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhai.shiro.realm.CustomRealm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomRealmTest {
	
	/**
	 * 简单版 没有加盐
	 */
	/*@Test
	public void authentication(){
		CustomRealm customRealm=new CustomRealm();
		
		DefaultSecurityManager securityManager=new DefaultSecurityManager();
		securityManager.setRealm(customRealm);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		//这里提交的UsernamePasswordToken 会被SimpleAuthenticationInfo验证，验证用户名密码是否正确
		UsernamePasswordToken token=new UsernamePasswordToken("jack", "123456");
		
		subject.login(token);
		
		
		try {
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			subject.checkRoles("user");
			subject.checkPermissions("user:select","user:add");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====="+e);
		}
		System.out.println("=====end");
		
	}*/
	
	/**
	 * shiro 加密 加盐
	 */
	@Test
	public void authenticationWithHash(){
		CustomRealm customRealm=new CustomRealm();
		
		DefaultSecurityManager securityManager=new DefaultSecurityManager();
		securityManager.setRealm(customRealm);
		
		//start
		HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");
		matcher.setHashIterations(1);//设置加密次数
		
		//在Realm中设置加密对象
		customRealm.setCredentialsMatcher(matcher);
		//end
		
		
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("jack", "123456");
		subject.login(token);
		
		
		try {
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			//subject.checkRoles("user");
			//subject.checkPermissions("user:select","user:add");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====="+e);
		}
		System.out.println("=====end");
		
	}

	
}
