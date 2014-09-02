/**
 * 
 */
package com.aokunsang.service;

import com.aokunsang.po.User;
import com.xiaomi.xmpush.server.Message;

import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
public interface MiPushService {
    public Message buildMessage() throws Exception;
    public void sendMessageToAliases() throws Exception;
}
