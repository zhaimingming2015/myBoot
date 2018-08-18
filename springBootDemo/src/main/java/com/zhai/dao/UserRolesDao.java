package com.zhai.dao;

import java.util.List;

import com.zhai.pojo.UserRoles;

public interface UserRolesDao {

	public List<UserRoles> queryRolesByUsername(String username);
}
