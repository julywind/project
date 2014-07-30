/**
 * 
 */
package com.aokunsang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aokunsang.dao.BaseDao;
import com.aokunsang.po.User;
import com.aokunsang.service.LoginService;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private BaseDao baseDao;
	static String addUser = "insert into user(username,password,first_name,last_name,birthday,age) values(:userName,:passWord,:firstName,:lastName,:birthday,:age);";
	static String getUser = "select * from user where username = ? and password = ?";
	
	@Override
	public User getUser(String userName, String password) {
		User user = baseDao.getObject(getUser, User.class, new Object[]{userName,password});
		return user;
	}

	@Override
	public void addUser(User user) {
		baseDao.saveOrUpdateObject(addUser, user);
	}
}
