/**
 * 
 */
package com.aokunsang.service.impl;

import com.aokunsang.dao.BaseDao;
import com.aokunsang.po.FtpUser;
import com.aokunsang.service.FtpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
@Service
public class FtpUserServiceImpl implements FtpUserService {

	@Autowired
	private BaseDao baseDao;
	static String addUser = "INSERT INTO FTP_USER (userid, userpassword,\n" +
            "homedirectory, enableflag, writepermission, idletime, uploadrate,\n" +
            "downloadrate) VALUES (':userId', ':userPassword:',\n" +
            "':homedirectory',':enableflag',\n" +
            "':writepermission', :idletime, :uploadrate:,:downloadrate)";
	static String getUser = "select * from FTP_USER where userid = ? and userpassword = md5(?)";
	static String duplicateUser = "select * from FTP_USER where userid = ? ";
	@Override
	public FtpUser getUser(String userName, String password) {
        FtpUser user = baseDao.getObject(getUser, FtpUser.class, new Object[]{userName,password});
		return user;
	}
	
	@Override
	public boolean duplicateUser(FtpUser user){
        FtpUser user1 = baseDao.getObject(duplicateUser, FtpUser.class, new Object[]{user.getUserId()}) ;
		if(null == user1){
			return false ;
		}
		return true;
	}
	
	@Override
	public void addUser(FtpUser user) {
		baseDao.saveOrUpdateObject(addUser, user);
	}
    @Override
    public List<FtpUser> query(String sql,String[] params)
    {
        return baseDao.getObjList(sql,FtpUser.class,params);
    }

    @Override
    public Integer getCount(String sql,String... params)
    {
        return baseDao.getForInt(sql, params);
    }
}
