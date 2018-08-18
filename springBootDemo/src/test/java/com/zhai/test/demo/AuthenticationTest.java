package com.zhai.test.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * shiro学习
 * @author ZHAIMINGMING
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationTest {
	
	SimpleAccountRealm accountRealm=new SimpleAccountRealm();
	
	
	@Before
	public void addUser(){
		System.out.println("==========addUser()");
		accountRealm.addAccount("jack", "123456","admin","user");
	}
	
	@Test
	public void authentication(){
		
		DefaultSecurityManager securityManager=new DefaultSecurityManager();
		securityManager.setRealm(accountRealm);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("jack", "123456");
		subject.login(token);
		
		
		//subject.logout();
		//System.out.println("====isAuthenticated:"+subject.isAuthenticated());
		
		try {
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			//subject.checkRoles("admin","user");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
	}

}
