/**
 * 
 */
package com.aokunsang.service;

import com.aokunsang.po.FtpUser;

import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public interface FtpUserService {
	public FtpUser getUser(String userName, String password);
	public boolean duplicateUser(FtpUser user) ;
	public void addUser(FtpUser user);
    public List<FtpUser> query(String sql, String[] params);
    public Integer getCount(String sql, String... params);
}
