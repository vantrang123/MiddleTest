package com.trangdv.example_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.trangdv.example_android.listeners.OnDatabaseChangedListeners;
import com.trangdv.example_android.model.Info;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context mContext;
    private static OnDatabaseChangedListeners changedListeners;
    public static final String DATABASE_NAME = "saved_info.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COMMA = ",";

    public static abstract class DatabaseItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_info";

        public static final String COLUMN_NAME_INFO_NAME = "info_name";
        public static final String COLUMN_NAME_INFO_AGE = "info_age";
        public static final String COLUMN_NAME_INFO_ADRESS = "info_address";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseItem.TABLE_NAME + " (" +
                    DatabaseItem._ID + " INTEGER PRIMARY KEY " + COMMA +
                    DatabaseItem.COLUMN_NAME_INFO_NAME + " TEXT " + COMMA +
                    DatabaseItem.COLUMN_NAME_INFO_AGE + " TEXT " + COMMA +
                    DatabaseItem.COLUMN_NAME_INFO_ADRESS + " TEXT " + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseItem.TABLE_NAME;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addInfo(String infoName, String infoAge, String infoAdress) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseItem.COLUMN_NAME_INFO_NAME, infoName);
        values.put(DatabaseItem.COLUMN_NAME_INFO_AGE, infoAge);
        values.put(DatabaseItem.COLUMN_NAME_INFO_ADRESS, infoAdress);
        long rowId = db.insert(DatabaseItem.TABLE_NAME, null, values);

        if (changedListeners != null) {
            changedListeners.onNewDatabaseEntryAdded();
        }

        return rowId;
    }

    public Info getItemAt(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                DatabaseItem._ID,
                DatabaseItem.COLUMN_NAME_INFO_NAME,
                DatabaseItem.COLUMN_NAME_INFO_AGE,
                DatabaseItem.COLUMN_NAME_INFO_ADRESS
        };
        Cursor cursor = db.query(DatabaseItem.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToPosition(position)) {
            Info item = new Info();
            item.setmId(cursor.getInt(cursor.getColumnIndex(DatabaseItem._ID)));
            item.setmName(cursor.getString(cursor.getColumnIndex(DatabaseItem.COLUMN_NAME_INFO_NAME)));
            item.setmAge(cursor.getString(cursor.getColumnIndex(DatabaseItem.COLUMN_NAME_INFO_AGE)));
            item.setmAddress(cursor.getString(cursor.getColumnIndex(DatabaseItem.COLUMN_NAME_INFO_ADRESS)));
            cursor.close();
            return item;
        }
        return null;
    }

    public void renameItem(Info item, String name, String infoAge, String infoAdress) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseItem.COLUMN_NAME_INFO_NAME, name);
        values.put(DatabaseItem.COLUMN_NAME_INFO_AGE, infoAge);
        values.put(DatabaseItem.COLUMN_NAME_INFO_ADRESS, infoAdress);
        database.update(DatabaseItem.TABLE_NAME, values, DatabaseItem._ID + "=" + item.getmId(), null);
        if (changedListeners != null) {
            changedListeners.onNewDatabaseEntryRenamed();
        }
    }

    public void removeItemWithId(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        db.delete(DatabaseItem.TABLE_NAME, "_ID=?", whereArgs);
        if (changedListeners != null) {
            changedListeners.onNewDatabaseEntryRemoved();
        }
    }

    public int getCount() {

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {DatabaseItem._ID};
        Cursor cursor = db.query(DatabaseItem.TABLE_NAME, projection, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public static void setChangedListeners(OnDatabaseChangedListeners databaseChangedListeners) {
        changedListeners = databaseChangedListeners;
    }

}
