package com.zhai.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zhai.common.annotation.Permission;
import com.zhai.pojo.User;
import com.zhai.pojo.Users;
import com.zhai.service.UserService;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/error"},method=RequestMethod.GET)
	public ModelAndView error(){
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        System.out.println("error----- request");
		return modelAndView;
	}
	
	@RequestMapping(value={"/login"},method=RequestMethod.GET)
	public ModelAndView beetl(){
		
		//Users users = userService.queryById("1");
		
		
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("login request");
        modelAndView.addObject("email", "apk2sf@163.com");
        modelAndView.setViewName("login");
		
		return modelAndView;
	}
	
	@RequestMapping(value={"/subLogin"},method=RequestMethod.POST)
	//@ResponseBody
	public ModelAndView login(User user){
		
		System.out.println("======user===="+user.getPassWord()+user.getUsername());
		Subject subject = SecurityUtils.getSubject();
		//用户在页面输入的用户名和密码
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(), user.getPassWord());
		
		try {
			token.setRememberMe(user.isRememberMe());
			
			subject.login(token);
			
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			//subject.checkRoles("admin");
			//subject.checkPermissions("user:select","user:add");
		} catch (Exception e) {
			 e.getMessage();
		}
		
		/*if(subject.hasRole("admin")){
			return "has Role admin";
		}*/
		
		/*if(subject.isPermitted("user:select")){
			return "has Permission user:select";
		}*/
		
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
		return modelAndView;
	}
	
	
	//@RequiresRoles("admin")
	//@RequiresPermissions("user:select")
	@RequestMapping(value={"/testRole"},method=RequestMethod.GET)
	//@ResponseBody
	public ModelAndView testRole(){
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
		return modelAndView;
	}
	
	//@RequiresRoles("admin1")
	//@RequiresPermissions("user:add")
	@RequestMapping(value={"/testRole1"},method=RequestMethod.GET)
	@ResponseBody
	public String testRole1(){
		return "testRole1 success!";
	}
	
	
	
	
	@RequestMapping(value={"/testPerms"},method=RequestMethod.GET)
	@ResponseBody
	public String testPerms(){
		return "testPerms success!";
	}
	
	
	
	@Permission
	@RequestMapping(value={"/testPerms1"},method=RequestMethod.GET)
	@ResponseBody
	public String testPerms1(){
		return "testPerms1 success!";
	}

}
