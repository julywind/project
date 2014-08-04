package com.aokunsang.authority;

/**
 * Created by marty on 14-8-4.
 */
public enum AuthorityType{
    // 包含了枚举的中文名称, 枚举的索引值
    WORKER("增删改查员工", 1),

    SALES_ORDER_CREATE("创建订单", 6),
    SALES_ORDER_FIND("查看订单", 7),
    SALES_ORDER_MODIFY("修改订单", 8),
    SALES_ORDER_DELETE("删除订单", 9),
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
