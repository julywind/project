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
	static String addUser = "insert into user(username,password,first_name,last_name,birthday,phone_number,authority,age) values(:userName,md5(:passWord),:firstName,:lastName,:birthday,:phoneNumber,:authority,:age);";
	static String getUser = "select * from user where username = ? and password = md5(?)";
	static String duplicateUser = "select * from user where username = ? ";
	@Override
	public User getUser(String userName, String password) {
		User user = baseDao.getObject(getUser, User.class, new Object[]{userName,password});
		return user;
	}
	
	@Override
	public boolean duplicateUser(User user){
		User user1 = baseDao.getObject(duplicateUser, User.class, new Object[]{user.getUserName()}) ;
		if(null == user1){
			return false ;
		}
		return true;
	}
	
	@Override
	public void addUser(User user) {
		baseDao.saveOrUpdateObject(addUser, user);
	}
}
