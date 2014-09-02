package com.aokunsang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private String fileName;
	private Long lastModify=-1l;
	private Properties p=new Properties();
	public Properties getP() {
		return p;
	}

	public void setP(Properties p) {
		this.p = p;
	}

	public Long getLastModify() {
		return lastModify;
	}
	public String getProperty(String key){
		this.touchFile();
		return p.getProperty(key);
	}

	public void setProperty(String key,String value){
		p.setProperty(key,value);
	}
	public PropertiesUtil(String fileName)
	{
		this.fileName=fileName;
		File file = new File(fileName);
		if(file.exists())
		{
			this.lastModify = file.lastModified();
			this.p=init();
		}
	}
	
	public boolean touchFile()
	{
		File file = new File(fileName);
		if(file.exists())
		{
			if(lastModify!=file.lastModified())
			{
				this.p=init();
				return true;
			}
		}
		return false;
	}
	public Properties init() {
		String configFileString = fileName;
		Properties p = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(configFileString);
			p.load(in);
			//p.getProperty("name");// 获取属�?�?

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (in != null)
			{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 如果此处直接 使用 visitFile 会报错，找不到文件�?
		return p;
	}
	// 写properties文件
	public boolean save()  {
		FileOutputStream out = null;
		try{
			out = new FileOutputStream(fileName);
			p.store(out, " visit update!");// 存储修改后属�?
			out.close();
		}catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}finally{
			if(out!=null)
			{
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	// 写properties文件
	public boolean update(String key, String value)  {
		FileOutputStream out = null;
		try{
		out = new FileOutputStream(fileName);
		p.setProperty(key, value);// 修改属�?
		p.store(out, " visit update!");// 存储修改后属�?
		out.close();
		}catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}finally{
			if(out!=null)
			{
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
