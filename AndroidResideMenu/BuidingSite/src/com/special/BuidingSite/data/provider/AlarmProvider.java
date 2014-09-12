package com.special.BuidingSite.data.provider;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;
import com.special.BuidingSite.data.AlarmContent;
import com.special.BuidingSite.data.DBHelper.DBHelper;
import com.special.BuidingSite.data.ProjectionMap;

/**
 * Created by Administrator on 2014/9/5.
 */
public class AlarmProvider extends ContentProvider{
    private static final String TAG = "AlarmProvider";
    private static final String  DATABASE_NAME = "alarm.db";
    private static final int UI_BASE = 1;
    private static final int UI_ALARM = UI_BASE;
    private static final int UI_ALARMS = UI_BASE + 1;

    public static final String AUTHORITY = "com.special.buildsite.provider";
    public static final String uiNotificationAuthority = "com.special.buildsite.provider.uinotifications";
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    private static Uri ALARM_CHECK_URI;
    private static Uri UIPROVIDER_ALARM_NOTIFIER;
    private static final ContentValues EMPTY_CONTENT_VALUES = new ContentValues();

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private static void init(final Context context) {
        // Synchronize on the matcher rather than the class object to minimize
        // risk of contention
        // & deadlock.
        synchronized (sURIMatcher) {
            // We use the existence of this variable as indicative of whether
            // this function has
            // already run.
            ALARM_CHECK_URI = Uri.parse("content://"
                    + AUTHORITY + "/alarm");

            UIPROVIDER_ALARM_NOTIFIER = Uri.parse("content://"
                    + uiNotificationAuthority + "/alarm/");
            /**
             * THIS URI HAS SPECIAL SEMANTICS ITS USE IS INTENDED FOR THE UI TO
             * MARK CHANGES THAT NEED TO BE SYNCED BACK TO A SERVER VIA A SYNC
             * ADAPTER
             */
            sURIMatcher.addURI(AUTHORITY, "alarms",
                    UI_ALARMS);
            sURIMatcher.addURI(AUTHORITY, "alarm/#",
                    UI_ALARM);
        }
    }
    /**
     * Wrap the UriMatcher call so we can throw a runtime exception if an
     * unknown Uri is passed in
     *
     * @param uri
     *            the Uri to match
     * @return the match value
     */
    private static int findMatch(Uri uri, String methodName) {

        int match = sURIMatcher.match(uri);
        if (match < 0) {
            throw new IllegalArgumentException("Unknown uri: " + uri);
        } else {
            Log.v(TAG, methodName + ": uri=" + uri + ", match is " + match);
        }
        return match;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(),DATABASE_NAME);
        init(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] uiProjection, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = dbHelper.getReadableDatabase();
        int match = findMatch(uri,"query");
        Cursor c = null;
        switch (match)
        {
            case UI_ALARM: {
                c = db.rawQuery(uiQuery(uri, uiProjection, selection), uiProjection);
                break;
            }
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        return c;
    }

    private String uiQuery(Uri uri, String[] uiProjection,
                           String selection)
    {
        final StringBuilder sb = genSelect(getMessageListMap(),
                uiProjection, EMPTY_CONTENT_VALUES);
        sb.append(" FROM " + AlarmContent.AlarmColumns.TABLE_NAME + " WHERE " + AlarmContent.AlarmColumns.ID
                + "=?");
        return sb.toString();
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db = dbHelper.getWritableDatabase();
        int match = findMatch(uri,"insert");
        switch (match)
        {
            case UI_ALARM:
                long rowId = db.insert(AlarmContent.AlarmColumns.TABLE_NAME,"foo",contentValues);
                if (rowId > 0) {
                    Uri rowUri = ContentUris.appendId(AlarmContent.AlarmColumns.CONTENT_URI.buildUpon(), rowId).build();
                    getContext().getContentResolver().notifyChange(rowUri, null);
                    return rowUri;
                }
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String sqlWhere, String[] strings) {
        db = dbHelper.getWritableDatabase();
        int match = findMatch(uri,"delete");
        int result;
        switch (match)
        {
            case UI_ALARM:
                result = db.delete(AlarmContent.AlarmColumns.TABLE_NAME,sqlWhere,strings);
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        if (result > 0) {
            notifyUI(UIPROVIDER_ALARM_NOTIFIER, null);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String whereSql, String[] strings) {
        db = dbHelper.getWritableDatabase();
        int match = findMatch(uri,"update");
        int result;
        switch (match)
        {
            case UI_ALARM:
                result = db.update(AlarmContent.AlarmColumns.TABLE_NAME,contentValues,whereSql,strings);
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        if (result > 0) {
            notifyUI(UIPROVIDER_ALARM_NOTIFIER, null);
        }
        return result;
    }

    private void notifyUI(Uri uri, String id) {
        final Uri notifyUri = (id != null) ? uri.buildUpon().appendPath(id)
                .build() : uri;
        getContext().getContentResolver().notifyChange(notifyUri, null);
    }

    private void notifyUI(Uri uri, long id) {
        notifyUI(uri, Long.toString(id));
    }

    /**
     * @author HouMingtao
     */
    private String preProcessSql(String sql) {
        if (sql == null || sql.isEmpty()) {
            return "";
        }
        sql = sql.replaceAll("/", "//");
        sql = sql.replaceAll("\\\\", "/\\\\");
        sql = sql.replaceAll("_", "/_");
        sql = sql.replaceAll("%", "/%");
        sql = sql.replaceAll("'", "''");
        sql = sql.replaceAll("\\[", "/\\[");
        sql = sql.replaceAll("-", "/-");
        sql = sql.replaceAll("&", "/&");
        sql = sql.replaceAll("\\|", "/\\|");
        sql = sql.replaceAll("\"", "/\"");
        return sql;
    }
    private static ProjectionMap sMessageListMap=null;
    /**
     * Mapping of UIProvider columns to EmailProvider columns for the message
     * list (called the conversation list in UnifiedEmail)
     */
    private static ProjectionMap getMessageListMap() {
        ProjectionMap pm = sMessageListMap;

        if (pm == null) {
            pm = ProjectionMap
                    .builder()
                    .add(BaseColumns._ID, AlarmContent.AlarmColumns.ID)
                    .add(AlarmContent.AlarmColumns.USER_NAME, AlarmContent.AlarmColumns.USER_NAME)
                    .add(AlarmContent.AlarmColumns.FILE_NAME, AlarmContent.AlarmColumns.FILE_NAME)
                    .add(AlarmContent.AlarmColumns.GEN_DATE, AlarmContent.AlarmColumns.GEN_DATE)
                    .build();
        }

        return pm;
    }

    private static StringBuilder genSelect(ProjectionMap map,
                                           String[] projection, ContentValues values) {
        final StringBuilder sb = new StringBuilder("SELECT ");
        boolean first = true;
        for (final String column : projection) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            final String val;
            // First look at values; this is an override of default behavior
            if (values.containsKey(column)) {
                final String value = values.getAsString(column);
                if (value == null) {
                    val = "NULL AS " + column;
                } else if (value.startsWith("@")) {
                    val = value.substring(1) + " AS " + column;
                } else {
                    val = "'" + value + "' AS " + column;
                }
            } else {
                // Now, get the standard value for the column from our
                // projection map
                final String mapVal = map.get(column);
                // If we don't have the column, return "NULL AS <column>", and
                // warn
                if (mapVal == null) {
                    val = "NULL AS " + column;
                    // Apparently there's a lot of these, so don't spam the log
                    // with warnings
                    // LogUtils.w(TAG, "column " + column +
                    // " missing from projection map");
                } else {
                    val = mapVal;
                }
            }
            sb.append(val);
        }
        return sb;
    }
    private static StringBuilder genSelect(ProjectionMap map,
                                           String[] projection) {
        return genSelect(map, projection, EMPTY_CONTENT_VALUES);
    }
}
