/**
 * 
 */
package com.aokunsang.util;

import org.springframework.core.NestedRuntimeException;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public class DaoException extends NestedRuntimeException {

	public DaoException(String msg) {
		super(msg);
	}
	
	public DaoException(String msg,Throwable obj) {
		super(msg,obj);
	}
}
