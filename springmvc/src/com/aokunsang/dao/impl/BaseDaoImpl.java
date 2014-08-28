/**
 * 
 */
package com.aokunsang.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aokunsang.dao.BaseDao;
import com.aokunsang.util.DBUtil;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
@Repository
public class BaseDaoImpl implements BaseDao {

	@Autowired
	private DBUtil util;

	@Override
	public void batchOperate(String sql, List<?> objs) {
		util.batchOperate(sql, objs);
	}

	@Override
	public int editObject(String sql, Object[] obj) {
		return util.editObject(sql, obj);
	}

	@Override
	public Map<String, ?> find(String sql, Object[] objs) {
		return util.getMap(sql, objs);
	}

	@Override
	public <T extends Serializable> List<T> getObjList(String sql,
			Class<T> className, Object[] objs) {
		return (List<T>)util.getObjList(sql, className, objs);
	}

	@Override
	public int isExist(String sql, Object[] obj) {
		return util.isExist(sql, obj);
	}

	@Override
	public <T extends Serializable> void saveOrUpdateObject(String sql, T entry) {
		util.addOrUpdate(sql, entry);
	}

	@Override
	public <T extends Serializable> T getObject(String sql,Class<T> clazz,Object[] objs) {
		return (T)util.getObject(sql, clazz, objs);
	}

    @Override
    public Integer getForInt(String sql, Object... objs) {
        return util.getForInt(sql, objs);
    }
}
