/**
 * 
 */
package com.aokunsang.service.impl;

import com.aokunsang.dao.BaseDao;
import com.aokunsang.service.MiPushService;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;
import com.xiaomi.xmpush.server.TargetedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
@Service
public class MiPushServiceImpl implements MiPushService {

	@Autowired
	private BaseDao baseDao;

    public static final String PACKAGE_NAME = "com.special.BuidingSite";
    public static final String APP_SECRET_KEY="";
    public MiPushServiceImpl()
    {
        super();
    }

    @Override
    public Message buildMessage() throws Exception {
        String messagePayload = "这是消息内容";
        String title = "通知标题";
        String description = "这是描述";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(PACKAGE_NAME)
                .passThrough(0)  //消息使用通知栏提示
                .notifyType(1)     // 使用默认提示音提示
                .build();
        return message;
    }

    private void sendMessage() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(APP_SECRET_KEY);
        String messagePayload= "This is a message";
        String title = "notification title";
        String description = "notification description";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(PACKAGE_NAME)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        //sender.send(message, "regId", 0); //根据regID，发送消息到指定设备上，不重试。
    }

    private void sendMessages() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(APP_SECRET_KEY);
        List<TargetedMessage> messages = new ArrayList<TargetedMessage>();
        TargetedMessage targetedMessage1 = new TargetedMessage();
        targetedMessage1.setTarget(TargetedMessage.TARGET_TYPE_ALIAS, "alias1");
        String messagePayload1= "This is a message1";
        String title1 = "notification title1";
        String description1 = "notification description1";
        Message message1 = new Message.Builder()
                .title(title1)
                .description(description1).payload(messagePayload1)
                .restrictedPackageName(PACKAGE_NAME)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        targetedMessage1.setMessage(message1);
        messages.add(targetedMessage1);
        TargetedMessage targetedMessage2 = new TargetedMessage();
        targetedMessage1.setTarget(TargetedMessage.TARGET_TYPE_ALIAS, "alias2");
        String messagePayload2= "This is a message2";
        String title2 = "notification title2";
        String description2 = "notification description2";
        Message message2 = new Message.Builder()
                .title(title2)
                .description(description2).payload(messagePayload2)
                .restrictedPackageName(PACKAGE_NAME)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        targetedMessage2.setMessage(message2);
        messages.add(targetedMessage2);
        sender.send(messages, 0); //根据alias，发送消息到指定设备上，不重试。
    }

    private void sendMessageToAlias() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(APP_SECRET_KEY);
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        String alias = "testAlias";    //alias非空白，不能包含逗号，长度小于128。
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(PACKAGE_NAME)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        sender.sendToAlias(message, alias, 0); //根据alias，发送消息到指定设备上，不重试。
    }

    @Override
    public void sendMessageToAliases() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(APP_SECRET_KEY);
        String messagePayload = "这是一个新消息";
        String title = "新消息标题";
        String description = "有新消息上传了";
        List<String> aliasList = new ArrayList<String>();
        aliasList.add("18612082092");  //alias非空白，不能包含逗号，长度小于128。
        aliasList.add("13701329128");  //alias非空白，不能包含逗号，长度小于128。
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(PACKAGE_NAME)
                .passThrough(0)  //消息使用通知栏提示
                .notifyType(1)     // 使用默认提示音提示
                .build();
        sender.sendToAlias(message, aliasList, 0); //根据aliasList，发送消息到指定设备上，不重试。
    }

    private void sendBroadcast() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(APP_SECRET_KEY);
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        String topic = "testTopic";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(PACKAGE_NAME)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        sender.broadcast(message, topic, 0); //根据topic，发送消息到指定一组设备上，不重试。
    }

}
