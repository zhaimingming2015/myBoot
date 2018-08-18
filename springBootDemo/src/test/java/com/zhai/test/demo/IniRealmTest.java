package com.zhai.test.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IniRealmTest {
	
	
	
	@Test
	public void authentication(){
		IniRealm iniRealm=new IniRealm("classpath:user.ini");
		
		DefaultSecurityManager securityManager=new DefaultSecurityManager();
		securityManager.setRealm(iniRealm);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("java", "123456");
		subject.login(token);
		
		
		//subject.logout();
		//System.out.println("====isAuthenticated:"+subject.isAuthenticated());
		
		
		try {
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			subject.checkRoles("admin");
			
			subject.checkPermission("user:select");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====="+e);
		}
		
		System.out.println("=====end");
		
		
	}

}
