package com.zhai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.zhai.mapper.UsersMapper;
import com.zhai.pojo.Users;
import com.zhai.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Override
	public Users queryById(String id) {
		Users users = usersMapper.selectByPrimaryKey(1);
		//System.out.println(users);
		return users;
	}

	@Override
	public void insert(Users user) {
		// TODO Auto-generated method stub
		usersMapper.insert(user);
	}

	@Override
	public Users queryByUsername(String username) {
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("username", username);
		
		
		//Users u=new Users();
		//u.setUsername(username);
		Users user = usersMapper.selectOneByExample(example);
		System.out.println("从数据库中根据用户名查询的结果："+user);
		
		return user;
	}

}
