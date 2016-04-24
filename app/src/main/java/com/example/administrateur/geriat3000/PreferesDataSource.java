package com.example.administrateur.geriat3000;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quentin on 24/04/16.
 */
public class PreferesDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_PRE_ID, MySQLiteHelper.COLUMN_PRE_MESSAGE};

    public PreferesDataSource(Context context) { dbHelper = new MySQLiteHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    public Prefere createPrefere(String message) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PRE_MESSAGE, message);

        long insertId = database.insert(MySQLiteHelper.TABLE_PREFERE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PREFERE, allColumns, MySQLiteHelper.COLUMN_PRE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Prefere newPrefere = cursorToPrefere(cursor);
        cursor.close();
        return newPrefere;
    }

    public List<Prefere> getAllPreferes() {
        List<Prefere> preferes = new ArrayList<Prefere>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PREFERE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Prefere prefere = cursorToPrefere(cursor);
            preferes.add(prefere);
            cursor.moveToNext();
        }
        cursor.close();
        return preferes;
    }

    private Prefere cursorToPrefere(Cursor cursor)
    {
        Prefere prefere = new Prefere();
        prefere.setId(cursor.getLong(0));
        prefere.setMessage(cursor.getString(1));
        return prefere;
    }
}
