package com.special.BuidingSite.data.DBHelper;

import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2014/9/5.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final int BODY_DATABASE_VERSION = 1;
    private Context mContext;
    public DBHelper(Context context, String name) {
        super(context, name, null, BODY_DATABASE_VERSION);
        mContext = context;
    }
    private final static String SQL_CREATE_TABLE=" create table alarm (integer primary key autoincrement," +
            "serverId integer, fileName text, userName text,flag_loaded tiny int default 0, genDate integer)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating EmailProviderBody database");
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    }
}
