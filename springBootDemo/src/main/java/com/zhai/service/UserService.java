package com.zhai.service;

import com.zhai.pojo.Users;

public interface UserService {

	public Users queryById(String id);
	
	public Users queryByUsername(String Username);
	
	public void insert(Users user);
}
