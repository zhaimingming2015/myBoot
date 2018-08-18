package com.zhai.test.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhai.dao.UserRolesDao;
import com.zhai.pojo.UserRoles;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRolesDaoTest {

	@Autowired
	private UserRolesDao userRolesDao;
	
	@Test
	public void queryByUsername(){
		 List<UserRoles> roles = userRolesDao.queryRolesByUsername("zhaimm");
		System.out.println(roles);
	}
}
