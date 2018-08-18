package com.zhai.pojo;

import javax.persistence.*;

@Table(name = "user_roles")
public class UserRoles {
    @Id
    private Integer id;

    private String username;

    @Column(name = "role_name")
    private String roleName;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	@Override
	public String toString() {
		return "UserRoles [id=" + id + ", username=" + username + ", roleName="
				+ roleName + "]";
	}

	
    
    
}