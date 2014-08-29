/**
 * 
 */
package com.aokunsang.po;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public class Alarm implements Serializable{
	private Integer id;
	private String userName;  //备注
	private String fileName; //文件路径
    private long genDate=System.currentTimeMillis(); //时间戳
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

    public long getGenDate() {
        return genDate;
    }

    public void setGenDate(long timeStamp) {
        this.genDate = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
