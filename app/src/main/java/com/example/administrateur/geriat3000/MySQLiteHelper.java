package com.example.administrateur.geriat3000;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by quentin on 22/04/16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_CONTACT = "contacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";

    public static final String TABLE_MESSAGE = "messages";
    public static final String COLUMN_ID_MESSAGE = "_id";
    public static final String COLUMN_CONTACT_ID = "contact_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_LEFT = "left";

    public static final String DATABASE_NAME = "geriat.db";
    public static final int DATABASE_VERSION = 5;

    private static final String DATABASE_CREATE_CONTACT = "create table " + TABLE_CONTACT + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, " + COLUMN_PHONE_NUMBER + " text not null);";

    private static final String DATABASE_CREATE_MESSAGE = "create table " + TABLE_MESSAGE + "("
            + COLUMN_ID_MESSAGE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CONTACT_ID + " int not null, "
            + COLUMN_LEFT + " int not null, "
            + COLUMN_MESSAGE + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CONTACT);
        database.execSQL(DATABASE_CREATE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion
                + " to " + newVersion + " which destroy all old data.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }
}
