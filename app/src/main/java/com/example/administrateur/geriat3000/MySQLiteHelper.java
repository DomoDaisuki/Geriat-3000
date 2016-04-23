package com.example.administrateur.geriat3000;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by quentin on 22/04/16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_CONTACT = "contacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "18811302655";

    public static final String DATABASE_NAME = "geriat.db";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + TABLE_CONTACT + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, " + COLUMN_PHONE_NUMBER + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) { database.execSQL(DATABASE_CREATE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion
                + " to " + newVersion + " which destroy all old data.");
        db.execSQL("DROP TABLE IF EXIST " + TABLE_CONTACT);
        onCreate(db);
    }
}
