/**
 * 
 */
package com.aokunsang.service.impl;

import com.aokunsang.dao.BaseDao;
import com.aokunsang.po.Alarm;
import com.aokunsang.po.User;
import com.aokunsang.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private BaseDao baseDao;
	static String addAlarm = "insert into alarm(file_name,user_name,gen_date) values(:fileName,:userName,:genDate)";
	static String getAlarm = "select * from alarm where id = ? ";
    static String deleteAlarm = "delete from alarm where id = ? ";
    static String deleteAlarms = "delete from alarm where id in (?) ";
    static String deleteAlarmByFilename = "delete from alarm where fileName = ? ";

	@Override
	public void addAlarm(Alarm alarm) {
		baseDao.saveOrUpdateObject(addAlarm, alarm);
	}

    @Override
    public Alarm getAlarm(Alarm alarm) {
        return baseDao.getObject(getAlarm, Alarm.class, new Object[]{alarm.getId()});
    }

    @Override
    public boolean deleteAlarm(Alarm alarm) {
        return baseDao.editObject(deleteAlarm, new String[]{String.valueOf(alarm.getId())})>0;
    }

    @Override
    public boolean deleteAlarmByFilename(String filename) {
        return baseDao.editObject(deleteAlarmByFilename, new String[]{filename})>0;
    }

    @Override
    public boolean deleteAlarm(String ids) {
        return baseDao.editObject(deleteAlarms, new String[]{ids})>0;
    }

    @Override
    public List<Alarm> query(String sql,String[] params) {
        return baseDao.getObjList(sql,Alarm.class,params);
    }

    @Override
    public int getCount(String sql,String... params)
    {
        return baseDao.getForInt(sql, params);
    }
}
