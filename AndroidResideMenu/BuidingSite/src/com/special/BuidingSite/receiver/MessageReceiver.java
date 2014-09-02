package com.special.BuidingSite.receiver;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.special.BuidingSite.CODE_TAG;
import com.special.BuidingSite.ui.PhoneApp;
import com.special.BuidingSite.R;
import com.xiaomi.mipush.sdk.*;

import java.util.List;
import java.util.Map;

/**
 * 1、PushMessageReceiver是个抽象类，该类继承了BroadcastReceiver。
 * 2、需要将自定义的DemoMessageReceiver注册在AndroidManifest.xml文件中 <receiver
 * android:exported="true"
 * android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"> <intent-filter>
 * <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" /> </intent-filter>
 * <intent-filter> <action android:name="com.xiaomi.mipush.ERROR" />
 * </intent-filter> </receiver>
 * 3、DemoMessageReceiver的onCommandResult方法用来接收客户端向服务器发送命令后的响应结果
 * 4、DemoMessageReceiver的onReceiveMessage方法用来接收服务器向客户端发送的消息
 * 5、onReceiveMessage和onCommandResult方法运行在非UI线程中
 * 
 * @author wangkuiwei
 */
public class MessageReceiver extends PushMessageReceiver {

    @Override
    public void onReceiveMessage(Context context, MiPushMessage message) {
        Log.v(PhoneApp.TAG,
                "onReceiveMessage is called. " + message.toString());
        String log = context.getString(R.string.recv_message, message.getContent());

        Message msg = Message.obtain();
        //if (message.isNotified()) {
            msg.obj = log;
        //}
        Map<String,String> extra = message.getExtra();
        String code_tag = extra.get("code");
        if(code_tag!=null)
        {
            if(code_tag.equals(CODE_TAG.CODE_FILE_DELETE))
            {
                //ToDo do sth. while delete
            }
        }
        PhoneApp.getHandler().sendMessage(msg);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Log.v(PhoneApp.TAG,
                "onCommandResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log = "";
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.register_success);
                try {
                    MiPushClient.setAlias(context, PhoneApp.getCurrentUser(context).getString("phoneNumber"),null);
                }catch(Exception e)
                {
                    log = context.getString(R.string.empty_phone_number_tip);
                }
            } else {
                log = context.getString(R.string.register_fail);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.set_alias_success, cmdArg1);
            } else {
                log = context.getString(R.string.set_alias_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.unset_alias_success, cmdArg1);
            } else {
                log = context.getString(R.string.unset_alias_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.subscribe_topic_success, cmdArg1);
            } else {
                log = context.getString(R.string.subscribe_topic_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.unsubscribe_topic_success, cmdArg1);
            } else {
                log = context.getString(R.string.unsubscribe_topic_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.set_accept_time_success, cmdArg1, cmdArg2);
            } else {
                log = context.getString(R.string.set_accept_time_fail, message.getReason());
            }
        } else {
            log = message.getReason();
        }

        Message msg = Message.obtain();
        msg.obj = log;
        PhoneApp.getHandler().sendMessage(msg);
    }

    public static class MessageHandler extends Handler {

        private Context context;

        public MessageHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            /*if (MainActivity.sMainActivity != null) {
                MainActivity.sMainActivity.refreshLogInfo();
            }*/
            if (!TextUtils.isEmpty(s)) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
