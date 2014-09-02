package com.aokunsang.service.impl;

import com.aokunsang.po.Alarm;
import com.aokunsang.service.AlarmService;
import org.apache.ftpserver.ftplet.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

public class FtpService extends DefaultFtplet {

    @Autowired
    private AlarmService alarmService;

    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        if(session!=null && session.isLoggedIn())
        {
            User ftpUser = session.getUser();
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
            alarmService.addAlarm(alarm);
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
            User ftpUser = session.getUser();
            String fileName = ftpUser.getHomeDirectory();
            if(!fileName.endsWith("/"))
            {
                fileName+="/";
            }
            fileName+=request.getArgument();
            alarmService.deleteAlarmByFilename(fileName);
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