/**
 * 
 */
package com.aokunsang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aokunsang.dao.BaseDao;
import com.aokunsang.po.User;
import com.aokunsang.service.LoginService;

import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private BaseDao baseDao;
	static String addUser = "insert into user(username,password,first_name,last_name,phone_number,authority,age) values(:userName,md5(:passWord),:firstName,:lastName,:phoneNumber,:authority,:age);";
	static String getUser = "select * from user where username = ? and password = md5(?)";
    static String getUserFromId = "select * from user where id=?";
	static String duplicateUser = "select * from user where username = ? ";
    static String updatePasswd = "update user set passWord=md5(?) where id=?";
	@Override
	public User getUser(String userName, String password) {
		User user = baseDao.getObject(getUser, User.class, new Object[]{userName,password});
		return user;
	}

    @Override
    public User getUser(User user) {
        return baseDao.getObject(getUserFromId, User.class, new Object[]{String.valueOf(user.getId())});
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
    @Override
    public List<User> query(String sql,String[] params)
    {
        return baseDao.getObjList(sql,User.class,params);
    }

    @Override
    public Integer getCount(String sql,Object... params)
    {
        return baseDao.getForInt(sql, params);
    }

    @Override
    public Integer updatePasswd(User user)
    {
        return baseDao.editObject(updatePasswd, new Object[]{user.getPassWord(),user.getId()});
    }
}
