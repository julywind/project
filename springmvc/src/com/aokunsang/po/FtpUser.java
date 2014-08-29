package com.aokunsang.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/8/30.
 */
public class FtpUser implements Serializable {
    private String userId;
    private String password;
    private String homedirectory;
    private boolean enableflag;
    private boolean writePermission;
    private int idletime;
    private int uploadrate;
    private int downloadrate;
    private int maxloginnumber;
    private int maxloginperip;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomedirectory() {
        return homedirectory;
    }

    public void setHomedirectory(String homedirectory) {
        this.homedirectory = homedirectory;
    }

    public boolean isEnableflag() {
        return enableflag;
    }

    public void setEnableflag(boolean enableflag) {
        this.enableflag = enableflag;
    }

    public boolean isWritePermission() {
        return writePermission;
    }

    public void setWritePermission(boolean writePermission) {
        this.writePermission = writePermission;
    }

    public int getIdletime() {
        return idletime;
    }

    public void setIdletime(int idletime) {
        this.idletime = idletime;
    }

    public int getUploadrate() {
        return uploadrate;
    }

    public void setUploadrate(int uploadrate) {
        this.uploadrate = uploadrate;
    }

    public int getDownloadrate() {
        return downloadrate;
    }

    public void setDownloadrate(int downloadrate) {
        this.downloadrate = downloadrate;
    }

    public int getMaxloginnumber() {
        return maxloginnumber;
    }

    public void setMaxloginnumber(int maxloginnumber) {
        this.maxloginnumber = maxloginnumber;
    }

    public int getMaxloginperip() {
        return maxloginperip;
    }

    public void setMaxloginperip(int maxloginperip) {
        this.maxloginperip = maxloginperip;
    }
}
