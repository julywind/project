/**
 * 
 */
package com.aokunsang.service;

import com.aokunsang.po.User;
import com.xiaomi.xmpush.server.Message;

import java.util.List;
import java.util.Map;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public interface MiPushService {
    public void sendMessageToAliases(List<String> receivers,Map<String,String> map) throws Exception;
}
