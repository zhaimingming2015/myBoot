package com.zhai.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.zhai.dao.UserRolesDao;
import com.zhai.mapper.UserRolesMapper;
import com.zhai.pojo.UserRoles;
import com.zhai.pojo.Users;


@Repository
public class UserRolesDaoImpl implements UserRolesDao {
	
	@Autowired
	private UserRolesMapper userRolesMapper;

	@Override
	public List<UserRoles> queryRolesByUsername(String username) {
		
		Example example = new Example(UserRoles.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("username", username);
		
		
		//Users u=new Users();
		//u.setUsername(username);
		List<UserRoles> list = userRolesMapper.selectByExample(example);
		System.out.println("从数据库中根据用户名查询的角色结果："+list.toString());
		
		return list;
	}

}
