package com.zhai.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * 自定义shiro的过滤器filter
 * @author ZHAIMINGMING
 *
 */
public class RolesOrFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest,
			ServletResponse servletResponse, Object obj) throws Exception {
		Subject subject = getSubject(servletRequest, servletResponse);
		//obj其实就是角色数据【访问请求所需要具有的角色信息】
		String[] roles=(String[])obj;
		System.out.println("---obj.hasRole:"+roles.length);
		if(roles==null ||roles.length==0){
			//不需要角色
			return true;
		}
		for(String role:roles){
			//【登录用户所具有的角色】
			if(subject.hasRole(role)){
				System.out.println("---subject.hasRole:"+role);
				return true;
			}
		}
		
		
		return false;
	}

}
