package com.zhai.shiro.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhai.pojo.UserRoles;
import com.zhai.service.RoleService;
import com.zhai.service.UserService;

/**
 * 自定义realm
 * 
 * @author ZHAIMINGMING
 * 
 */
public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	

	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	Map<String, String> userMap = new HashMap<String, String>(16);
	{
		//userMap.put("jack", "123456");
		//userMap.put("jack", "e10adc3949ba59abbe56e057f20f883e");//加密未加盐
		userMap.put("jack", "f56af8be40aa489f60d6d74c6993f736");//加密 且加盐
		//super.setName("customRealm");
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {

		String username = (String) principalCollection.getPrimaryPrincipal();
		// 从数据库或缓存中 根据用户名获取角色数据

		Set<String> roles = getRolesByUsername(username);
		// 从数据库或缓存中 根据用户名获取权限数据
		Set<String> permissions = getPermissionsByUsername(username);
		
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		authorizationInfo.setStringPermissions(permissions);
		authorizationInfo.setRoles(roles);
		System.out.println("-----CustomRealm--doGetAuthorizationInfo==roles:"+roles.toString()+"permissions:"+permissions.toString());
		
		return authorizationInfo;
		//return null;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 1、从主体传过来的认证信息中获得用户名
		String username = (String) authenticationToken.getPrincipal();
		//2 通过用户名到数据库中获取凭证
		String password = getPasswordByUsername(username);
		//System.out.println("=====CustomRealm==doGetAuthenticationInfo");

		if (password == null) {
			return null;
		}
		System.out.println("=====CustomRealm=doGetAuthenticationInfo=password==print:" + password);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				username, password, "customRealm");

		//设置盐值
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("zhai"));
		
		return authenticationInfo;
	}

	/**
	 * 模拟数据库查询
	 * 真实数据库查询
	 * @param username
	 * @return
	 */
	private String getPasswordByUsername(String username) {

		return userService.queryByUsername(username).getPassword();
		
		//System.out.println("====模拟从数据库获取密码："+userMap.get(username));
		//return userMap.get(username);
	}

	/**
	 * 模拟数据库查询角色信息
	 * 
	 * @param username
	 * @return
	 */
	private Set<String> getRolesByUsername(String username) {
		List<UserRoles> roles = roleService.getRolesByUsername(username);
		
		System.out.println("from DB getRoles");
		Set<String> set = new HashSet<String>();
		for(UserRoles role:roles){
			set.add(role.getRoleName());
		}
		//set.add("admin");
		//set.add("user");
		return set;

	}
	/**
	 * 模拟数据库查询权限信息
	 * @param username
	 * @return
	 */
	private Set<String> getPermissionsByUsername(String username) {
		System.out.println("from DB getPermissions");
		Set<String> set = new HashSet<String>();
		set.add("user:select");
		set.add("user:add");
		return set;

	}
	
	public static void main(String[] args) {
		
		//Md5Hash md5Hash=new Md5Hash("123456"); //只加密
		Md5Hash md5Hash=new Md5Hash("123456", "zhai");//加密且加盐f56af8be40aa489f60d6d74c6993f736
		System.out.println(md5Hash.toString());
	}

}
