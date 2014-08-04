package com.aokunsang.authority;

/**
 * Created by marty on 14-8-4.
 */
public class AuthorityHelper {

    /**
     * 判断是否有权限
     * @param akey  aString中位置的索引值,也就是权限位
     * @param aString  权限字段,比如 11010101011101
     * @return
     */
    public static boolean hasAuthority(int akey,String aString){
        if(aString==null || "".equals(aString) || aString.length()<=akey){
            return false;
        }

        char value = aString.charAt(akey);
        if(value == '1'){
            return true;
        }

        return false;
    }
}
