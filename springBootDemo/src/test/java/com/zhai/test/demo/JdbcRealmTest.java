package com.zhai.test.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.pool.DruidDataSource;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class JdbcRealmTest {
	
	DruidDataSource dataSource=new DruidDataSource();
	
	{
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
	}
	
	/**
	 * 简单版 使用默认的sql语句
	 */
	/*@Test
	public void authentication(){
		
		JdbcRealm jdbcRealm=new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);//查询权限数据的开关
		
		//默认的sql语句
		//DEFAULT_AUTHENTICATION_QUERY = "select password from 【users】 where username = ?";
		//DEFAULT_USER_ROLES_QUERY = "select role_name from 【user_roles】 where username = ?";
		//DEFAULT_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";
		
		//
		
		String sql="select password from test_user where user_name = ?";
		jdbcRealm.setAuthenticationQuery(sql);
		
		String roleSQL="select role_name from test_user_roles where username = ?";
		jdbcRealm.setUserRolesQuery(roleSQL);
		
		//1 构建securityManager
		DefaultSecurityManager securityManager=new DefaultSecurityManager();
		securityManager.setRealm(jdbcRealm);
		
		//2  主体提交认证请求
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("time", "123123");
		subject.login(token);
		
		
		try {
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			subject.checkRoles("admin");
			subject.checkPermission("user:select");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====="+e);
		}
		System.out.println("=====end");
		
	}*/
	
	/**
	 * 升级版 使用自定义的sql语句
	 */
	@Test
	public void authenticationWithSQL(){
		System.out.println("===Method:===authenticationWithSQL==start");
		
		JdbcRealm jdbcRealm=new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);
		
		String sql="select password from test_user where user_name = ?";
		jdbcRealm.setAuthenticationQuery(sql);
		
		String roleSQL="select role_name from test_user_roles where username = ?";
		jdbcRealm.setUserRolesQuery(roleSQL);
		
		//构建securityManager
		DefaultSecurityManager securityManager=new DefaultSecurityManager();
		securityManager.setRealm(jdbcRealm);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("xiaoming", "654321");
		subject.login(token);
		
		
		try {
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			subject.checkRoles("user");
			//subject.checkPermission("user:select");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====="+e);
		}
		System.out.println("=====end");
		
	}

}
