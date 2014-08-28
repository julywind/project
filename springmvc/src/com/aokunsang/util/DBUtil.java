/**
 * 
 */
package com.aokunsang.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
@Repository(value="util")
public class DBUtil{

	private Log log = LogFactory.getLog(DBUtil.class);
	
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	/**
	 * 添加或者更新数据
	 * @param sql
	 * @param obj
	 * @return
	 */
	public Integer addOrUpdate(String sql,Object obj){
		Integer id = 0;
		try {
			id = simpleJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(obj));
		} catch (Exception e) {
			log.info(e);
            return null;
            //throw new DaoException("数据库操作失败！",e);
		}
		return id;
	}
	
	/**
	 * 获取List集合
	 * @param sql
	 * @param className
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> getObjList(String sql,Class<?> className,Object[] obj){
		
		List<?> array = null;
		try {
            array =  this.simpleJdbcTemplate.getJdbcOperations().query(sql, obj, new BeanPropertyRowMapper(className));
		} catch (Exception e) {
			log.info(e);
            e.printStackTrace();
            return null;
			//throw new DaoException("数据库操作失败！",e);
		}
		return array;
	}
	
	/**
	 * 获取Map集合值
	 * @param sql
	 * @param obj
	 * @return
	 */
	public Map<String,?> getMap(String sql,Object[] obj){
		
		Map<String, ?> map = null;
		try {
			map = simpleJdbcTemplate.queryForMap(sql, obj);
		} catch (Exception e) {
			log.info(e);
            return null;
			//throw new DaoException("数据库操作失败！",e);
		}
		
		return map;
	}
	
	/**
	 * 获取相应的Object
	 * @param sql
	 * @param className
	 * @param obj
	 * @return
	 */
	public Object getObject(String sql,Class<? extends Serializable> className,Object[] obj){
		
		Object object = null;
		try {
			object = simpleJdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(className), obj);
		} catch (DataAccessException e) {
			log.info(e);
            return null;
			//throw new DaoException("数据库操作失败！",e);
		}
		return object;
	}
	
	/**
	 * 批量操作
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int[] batchOperate(String sql,List<?> obj){
		
		int[] a = null;
		try {
			a = simpleJdbcTemplate.batchUpdate(sql, 
													SqlParameterSourceUtils.createBatch(obj.toArray()));
		} catch (Exception e) {
			log.info(e);
            return null;
            //throw new DaoException("数据库操作失败！",e);
		}
		return a;
	}
	
	/**
	 * 检查是否有值
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int isExist(String sql,Object[] obj){
		int index = 0;
		try {
			index = simpleJdbcTemplate.queryForInt(sql, obj);
		} catch (Exception e) {
			log.info(e);
            return 0;
            //throw new DaoException("数据库操作失败！",e);
		}
		return index;
	}
	
	/**
	 * 编辑操作(增删改查都可以)
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int editObject(String sql,Object[] obj){
		int index = 0;
		try {
			index = simpleJdbcTemplate.update(sql, obj);
		} catch (DataAccessException e) {
			log.info(e);
            return 0;
            //throw new DaoException("数据库操作失败！",e);
		}
		return index;
	}

    /**
	 * @param simpleJdbcTemplate the simpleJdbcTemplate to set
	 */
	@Resource(name="dataSource")
	public void setSimpleJdbcTemplate(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
}
