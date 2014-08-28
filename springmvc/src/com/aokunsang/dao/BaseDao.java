/**
 * 
 */
package com.aokunsang.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
public interface BaseDao {

	/**
	 * 保存或者更新实体
	 * @param sql
	 * @param entry
	 */
	<T extends Serializable> void saveOrUpdateObject(String sql,T entry);
	
	/**
	 * 查询实体列表
	 * @param sql
	 * @param className
	 * @param obj
	 * @return
	 */
	<T extends Serializable> List<T> getObjList(String sql,Class<T> className,Object[] objs);
	
	/**
	 * 查询实体
	 * @param <T>
	 * @param sql
	 * @param objs
	 * @return
	 */
	<T extends Serializable> T getObject(String sql,Class<T> clazz,Object[] objs);

    /**
     * 查询数量
     * @param <T>
     * @param sql
     * @param objs
     * @return
     */
    Integer getForInt(String sql,Object... objs);
	
	/**
	 * 查询一个Map集合
	 * @param sql
	 * @param objs
	 * @return
	 */
	Map<String,?> find(String sql,Object[] objs);
	
	/**
	 * 批量操作
	 * @param sql
	 * @param objLs
	 */
	void batchOperate(String sql,List<?> objLs);
	
	/**
	 * 判断实体是否存在
	 * @param sql
	 * @param obj
	 * @return
	 */
	int isExist(String sql,Object[] obj);
	
	/**
	 * 编辑操作
	 * @param sql
	 * @param obj
	 * @return
	 */
	int editObject(String sql,Object[] obj);
}
