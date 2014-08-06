/**
 * 
 */
package com.aokunsang.service;

import com.aokunsang.po.User;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public interface LoginService {

	public User getUser(String userName,String password);
	public boolean duplicateUser(User user) ;
	public void addUser(User user);
	
}
