package com.aokunsang;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/8/27.
 */
public class CountBean implements Serializable {
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
