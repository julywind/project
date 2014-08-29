/**
 * 
 */
package com.aokunsang.service;

import com.aokunsang.po.User;

import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public interface LoginService {
	public User getUser(String userName,String password);
    public User getUser(User user);
	public boolean duplicateUser(User user) ;
	public void addUser(User user);
    public List<User> query(String sql,String[] params);
    public Integer getCount(String sql,Object... params);
    public Integer updatePasswd(User user);
}
