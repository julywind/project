package com.aokunsang;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/8/27.
 */
public class JsonResultBean implements Serializable {
    private boolean result;
    private int totalCount;
    private Object data;
    public JsonResultBean(boolean result, Object data)
    {
        this.setResult(result);
        this.setData(data);
    }

    public JsonResultBean(boolean result,int totalCount, Object data)
    {
        this.setTotalCount(totalCount);
        this.setResult(result);
        this.setData(data);
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
