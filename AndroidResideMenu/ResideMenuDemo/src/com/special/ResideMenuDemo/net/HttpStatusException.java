package com.special.ResideMenuDemo.net;

/**
 * Created by Administrator on 2014/8/31.
 */
public class HttpStatusException extends  Exception{
    private int status;
    public HttpStatusException(int httpStatus, String msg)
    {
        super(msg);
        this.status = httpStatus;
    }
    public int getStatus()
    {
        return status;
    }
}
