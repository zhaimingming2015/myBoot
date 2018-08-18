package com.zhai.test.service;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhai.pojo.Users;
import com.zhai.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void query(){
		Users user = userService.queryByUsername("zhaimm");
		System.out.println(user);
	}
	
	@Test
	public void insert(){
		Users user = new Users();
		user.setUsername("zhaimm");
		String password="654321";
		user.setPassword(encryptWithSalt(password));
		System.out.println(encryptWithSalt(password));
		
		userService.insert(user);
	}
	
	public String encryptWithSalt(String password){
		Md5Hash md5Hash=new Md5Hash(password, "zhai");//加盐
		return md5Hash.toString();
	}

}
