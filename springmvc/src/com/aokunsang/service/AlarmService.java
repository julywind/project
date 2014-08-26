/**
 * 
 */
package com.aokunsang.service;

import com.aokunsang.po.Alarm;
import com.aokunsang.po.User;

import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public interface AlarmService {

	public Alarm getAlarm(Alarm alarm);
    public void addAlarm(Alarm alarm);
	public boolean deleteAlarm(User user);
    public boolean deleteAlarm(String ids) ;
	public List<Alarm> query(String sql,String[] params);
	
}
