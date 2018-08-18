package com.zhai.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.zhai.common.utils.RedisOperator;
import com.zhai.pojo.User;
import com.zhai.pojo.Users;
import com.zhai.service.UserService;



@RestController
public class TestController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisOperator redisOperator;
	
	@Autowired
	private UserService userService;

	
	@RequestMapping(value={"/hello/{id}"},method=RequestMethod.GET)
	public String hello(@PathVariable("id") Integer id){
		Users users = userService.queryById("1");
		
		System.out.println(JSONUtils.toJSONString(users));
		//stringRedisTemplate.opsForValue().set("uuid", "immoc");
		return "maossssaaaaid3333333333"+id+"--value is :"+stringRedisTemplate.opsForValue().get("uuid");
	} 
	
	@RequestMapping(value={"/beetl/add"},method=RequestMethod.GET)
	public ModelAndView testBeetl(){
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("add request");
        modelAndView.addObject("email", "apk2sf@163.com");
        modelAndView.setViewName("add");
		
		return modelAndView;
	}
	
	/*@RequestMapping(value={"/add"},method=RequestMethod.GET)
	public ModelAndView beetl(){
		
		Users users = userService.queryById("1");
		
		
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("add request");
        modelAndView.addObject("email", "apk2sf@163.com");
        modelAndView.setViewName("login");
		
		return modelAndView;
	}
	//@RequiresRoles
	@RequestMapping(value={"/subLogin"},method=RequestMethod.POST)
	@ResponseBody
	public String login(User user){
		
		System.out.println("======user===="+user.getPassWord()+user.getUsername());
		Subject subject = SecurityUtils.getSubject();
		//用户在页面输入的用户名和密码
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(), user.getPassWord());
		
		try {
			subject.login(token);
			
			System.out.println("====isAuthenticated:"+subject.isAuthenticated());
			//subject.checkRoles("admin");
			//subject.checkPermissions("user:select","user:add");
		} catch (Exception e) {
			return e.getMessage();
		}
		
		if(subject.hasRole("admin")){
			return "has Role admin";
		}
		
		if(subject.isPermitted("user:select")){
			return "has Permission user:select";
		}
		
		return "no Permission user:select";
	}*/
	
	@RequestMapping(value={"/redis"},method=RequestMethod.GET)
	@ResponseBody
	public String redis(){
		
		
		//redisOperator.set("uuid", "immoc_zhaimm", 2000*10);
		stringRedisTemplate.opsForValue().set("uuid44455", "55immoc_zhaimm333");
		
		String valueByKey = redisOperator.getValueByKey("uuid");
		
		return "redis success!"+valueByKey;
	}
}
