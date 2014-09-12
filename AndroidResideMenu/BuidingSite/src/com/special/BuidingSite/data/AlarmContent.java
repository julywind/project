package com.special.BuidingSite.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2014/9/11.
 */
public class AlarmContent {
    public static final String AUTHORITY  = "com.special.BuidingSite.provider";

    // BaseColumn类中已经包含了 _id字段
    public static final class AlarmColumns implements BaseColumns {
        public static final Uri CONTENT_URI  = Uri.parse("content://"+AUTHORITY);
        public static final String TABLE_NAME="alarm";
        // 表数据列
        public static final String  ID  = "_id";
        public static final String  USER_NAME  = "userName";
        public static final String  FILE_NAME  = "fileName";
        public static final String  GEN_DATE  = "genDate";
    }
}
