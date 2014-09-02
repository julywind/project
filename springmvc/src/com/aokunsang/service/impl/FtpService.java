package com.aokunsang.service.impl;

import com.aokunsang.po.Alarm;
import com.aokunsang.po.User;
import com.aokunsang.service.AlarmService;
import com.aokunsang.CODE_TAG;
import com.aokunsang.service.LoginService;
import com.aokunsang.service.MiPushService;
import com.aokunsang.util.PropertiesUtil;
import org.apache.ftpserver.ftplet.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

public class FtpService extends DefaultFtplet {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private MiPushService miPushService;

    @Autowired
    private LoginService loginService;

    private final static int MAX_USER_NUM_ONCE_SENT=1024;
    // 类加载的路径
    public static String classloaderPath = FtpService.class.getClassLoader().getResource("/").getPath().replaceFirst("file:/", "");
    //获取当前项目的根路径
    public static PropertiesUtil properties = new PropertiesUtil(classloaderPath+"notify_msg.properties");

    /**
     * 推送消息给所有有手机号的用户,因为手机号将会作为设备的别名，且能保证每个用户只有最后一个注册别名的设备能接收到该消息
     * @throws Exception
     */
    private void notifyAllPhoneAlarmChanged(Alarm alarm, String codeTag) throws Exception {
        List<User> userList = loginService.getAllUserPhoneNumbers();
        List<List<String>> receivers = new LinkedList<List<String>>();
        List<String> tempReceiver = new LinkedList<String>();

        for(int idx=0 ; idx<userList.size();idx++)
        {
            tempReceiver.add(userList.get(idx).getPhoneNumber());
            if((idx+1)%MAX_USER_NUM_ONCE_SENT ==0)
            {
                receivers.add(tempReceiver);
                tempReceiver=new LinkedList<String>();
            }
        }
        if(!tempReceiver.isEmpty())
        {
            receivers.add(tempReceiver);
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("description",properties.getProperty("payload"));
        map.put("title",properties.getProperty("title"));
        if(alarm.getId()==null) {
            map.put("payload", properties.getProperty("description_new"));
        }else
        {
            map.put("payload", properties.getProperty("description_updated")+"  alarmServerId="+alarm.getId());
        }

        map.put("code",codeTag);
        map.put("id",alarm.getId()+"");
        for(List<String> recs : receivers)
        {
            miPushService.sendMessageToAliases(recs,map);
        }
    }

    /**
     * 推送消息给所有有手机号的用户,因为手机号将会作为设备的别名，且能保证每个用户只有最后一个注册别名的设备能接收到该消息
     * @throws Exception
     */
    private void notifyAllPhoneServerRetart() throws Exception {
        List<User> userList = loginService.getAllUserPhoneNumbers();
        List<List<String>> receivers = new LinkedList<List<String>>();
        List<String> tempReceiver = new LinkedList<String>();

        for(int idx=0 ; idx<userList.size();idx++)
        {
            tempReceiver.add(userList.get(idx).getPhoneNumber());
            if((idx+1)%MAX_USER_NUM_ONCE_SENT ==0)
            {
                receivers.add(tempReceiver);
                tempReceiver=new LinkedList<String>();
            }
        }
        if(!tempReceiver.isEmpty())
        {
            receivers.add(tempReceiver);
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("description",properties.getProperty("payload"));
        map.put("title",properties.getProperty("title"));
        map.put("payload", properties.getProperty("service_restart"));

        map.put("code",CODE_TAG.CODE_SERVER_START);
        for(List<String> recs : receivers)
        {
            miPushService.sendMessageToAliases(recs,map);
        }
    }
    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        if(session!=null && session.isLoggedIn())
        {
            org.apache.ftpserver.ftplet.User ftpUser = session.getUser();
            Alarm alarm = new Alarm();
            alarm.setUserName(ftpUser.getName());
            String fileName = ftpUser.getHomeDirectory();
            if(!fileName.endsWith("/"))
            {
                fileName+="/";
            }
            fileName+=request.getArgument();
            alarm.setFileName(fileName);
            alarm.setGenDate(System.currentTimeMillis());

            List<Alarm> alarmsInDb = alarmService.query("select * from alarm where file_name = ?",new String[]{alarm.getFileName()});
            if(alarmsInDb==null||!alarmsInDb.isEmpty()) {
                alarmService.addAlarm(alarm);
            }
            try {
                if(alarmsInDb == null || alarmsInDb.isEmpty())
                {
                    notifyAllPhoneAlarmChanged(alarm, CODE_TAG.CODE_FILE_NEW);
                }else
                {
                    notifyAllPhoneAlarmChanged(alarmsInDb.get(0),CODE_TAG.CODE_FILE_UPDATED);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onUploadEnd(session, request);
    }

    @Override
    public void init(FtpletContext ftpletContext) throws FtpException {
        // TODO Auto-generated method stub
        super.init(ftpletContext);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        /*try {
            notifyAllPhoneServerRetart();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        super.destroy();
    }

    @Override
    public FtpletResult onConnect(FtpSession session) throws FtpException,
            IOException {
        // TODO Auto-generated method stub
        return super.onConnect(session);
    }

    @Override
    public FtpletResult onDisconnect(FtpSession session) throws FtpException,
            IOException {
        // TODO Auto-generated method stub
        return super.onDisconnect(session);
    }

    @Override
    public FtpletResult beforeCommand(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.beforeCommand(session, request);
    }

    @Override
    public FtpletResult afterCommand(FtpSession session, FtpRequest request,
                                     FtpReply reply) throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.afterCommand(session, request, reply);
    }

    @Override
    public FtpletResult onLogin(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onLogin(session, request);
    }

    @Override
    public FtpletResult onDeleteStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onDeleteStart(session, request);
    }

    @Override
    public FtpletResult onDeleteEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        if(session!=null && session.isLoggedIn())
        {
            org.apache.ftpserver.ftplet.User ftpUser = session.getUser();
            String fileName = ftpUser.getHomeDirectory();
            if(!fileName.endsWith("/"))
            {
                fileName+="/";
            }
            fileName+=request.getArgument();
            alarmService.deleteAlarmByFilename(fileName);
            try{
                Alarm alart = new Alarm();
                alart.setFileName(fileName);
                notifyAllPhoneAlarmChanged(alart, CODE_TAG.CODE_FILE_DELETE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onDeleteEnd(session, request);
    }

    @Override
    public FtpletResult onUploadStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onUploadStart(session, request);
    }

    @Override
    public FtpletResult onDownloadStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onDownloadStart(session, request);
    }

    @Override
    public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onDownloadEnd(session, request);
    }

    @Override
    public FtpletResult onRmdirStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onRmdirStart(session, request);
    }

    @Override
    public FtpletResult onRmdirEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onRmdirEnd(session, request);
    }

    @Override
    public FtpletResult onMkdirStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onMkdirStart(session, request);
    }

    @Override
    public FtpletResult onMkdirEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onMkdirEnd(session, request);
    }

    @Override
    public FtpletResult onAppendStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onAppendStart(session, request);
    }

    @Override
    public FtpletResult onAppendEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onAppendEnd(session, request);
    }

    @Override
    public FtpletResult onUploadUniqueStart(FtpSession session,
                                            FtpRequest request) throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onUploadUniqueStart(session, request);
    }

    @Override
    public FtpletResult onUploadUniqueEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onUploadUniqueEnd(session, request);
    }

    @Override
    public FtpletResult onRenameStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onRenameStart(session, request);
    }

    @Override
    public FtpletResult onRenameEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onRenameEnd(session, request);
    }

    @Override
    public FtpletResult onSite(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        // TODO Auto-generated method stub
        return super.onSite(session, request);
    }
}