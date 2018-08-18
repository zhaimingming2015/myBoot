package com.zhai.pojo;

import javax.persistence.*;

public class Users {
    @Id
    private Integer id;

    private String username;

    private String password;

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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password="
				+ password + "]";
	}
    
    
    
}