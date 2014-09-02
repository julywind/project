import java.util.ArrayList;
import java.util.List;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;


public class Test {
	public static final String PACKAGE_NAME = "com.special.BuidingSite";
    public static final String APP_SECRET_KEY="bTf0zK6nR9PeW85L0yykuQ==";
    
	public static void sendMessageToAliases() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(APP_SECRET_KEY);
        String title = "新消息标题";
        String description = "有新消息上传了";
        String messagePayload = "";
        List<String> aliasList = new ArrayList<String>();
        aliasList.add("18612082092");  //alias非空白，不能包含逗号，长度小于128。
        Message message = new Message.Builder()
        .title(title)
        .description(description)
        .payload(messagePayload)
        .restrictedPackageName(PACKAGE_NAME)
        .passThrough(0)  //0 消息使用通知栏提示 1不使用通知栏提醒
        .notifyType(-1)   // 使用默认提示音提示
        .extra("notify_foreground","1")
        .extra("key1","1")
        .build();
        Result te = sender.sendToAlias(message, aliasList, 0); //根据aliasList，发送消息到指定设备上，不重试。
        System.out.println(te.toString());
    }
public static void main(String[] args) {
	/*HashMap<String,Long> ids = new HashMap<String, Long>();
	ids.put("1", 1l);
	ids.put("2", 1l);
	ids.put("3", 1l);
	System.out.println(ids.values().toString());*/
	try {
		Test.sendMessageToAliases();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*List<String> list = new ArrayList<String>();
	String test = "1";
	for(int i=0;i<10;i++)
	{
		test = i+"";
		list.add(test);
	}
	System.out.println(list.toString());*/
}
}
