package com.example.administrateur.geriat3000;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quentin on 23/04/16.
 */
public class MessagesDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID_MESSAGE, MySQLiteHelper.COLUMN_CONTACT_ID, MySQLiteHelper.COLUMN_MESSAGE, MySQLiteHelper.COLUMN_LEFT};

    public MessagesDataSource(Context context) { dbHelper = new MySQLiteHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    public MessageDb createMessage(long contactId, String message, int left)
    {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CONTACT_ID, contactId);
        values.put(MySQLiteHelper.COLUMN_MESSAGE, message);
        values.put(MySQLiteHelper.COLUMN_LEFT, left);

        long insertId = database.insert(MySQLiteHelper.TABLE_MESSAGE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGE, allColumns, MySQLiteHelper.COLUMN_ID_MESSAGE + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        MessageDb newMessage = cursorToMessage(cursor);
        cursor.close();
        return newMessage;
    }

    public List<MessageDb> getAllMessagesWithContactId(long contactId) {
        List<MessageDb> messages = new ArrayList<MessageDb>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MessageDb message = cursorToMessage(cursor);
            if (message.getContactId() == contactId)
                messages.add(message);
            cursor.moveToNext();
        }
        cursor.close();
        return messages;
    }

    private MessageDb cursorToMessage(Cursor cursor) {
        MessageDb message = new MessageDb();
        message.setId(cursor.getLong(0));
        message.setContactId(cursor.getLong(1));
        message.setMessage(cursor.getString(2));
        message.setLeft(cursor.getInt(3));
        return message;
    }

}
