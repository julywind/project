package com.aokunsang;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/8/27.
 */
public class ResultBean  implements Serializable {
    private boolean result;
    private Object data;
    public ResultBean(boolean result,Object data)
    {
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
}
