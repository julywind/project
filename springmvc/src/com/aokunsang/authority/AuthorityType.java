package com.aokunsang.authority;

/**
 * Created by marty on 14-8-4.
 */
public enum AuthorityType{
    // 包含了枚举的中文名称, 枚举的索引值
    USER_NORMAL("普通用户", 0),
    USER_MANAGE("管理员", 1),
    ;
    private String name;
    private int index;

    private AuthorityType(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
