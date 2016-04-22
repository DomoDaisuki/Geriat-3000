package com.example.administrateur.geriat3000;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quentin on 23/04/16.
 */
public class ContactsDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME};

    public ContactsDataSource(Context context) { dbHelper = new MySQLiteHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    public Contact createContact(String contact) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, contact);
        long insertId = database.insert(MySQLiteHelper.TABLE_CONTACT, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACT, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Contact newContact = cursorToContact(cursor);
        cursor.close();
        return newContact;
    }

    public void deleteContact(Contact contact) {
        long id = contact.getId();
        System.out.println("Contact deleted with id: " + id + " and name: " + contact.getName());
        database.delete(MySQLiteHelper.TABLE_CONTACT, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACT, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Contact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }

    private Contact cursorToContact(Cursor cursor)
    {
        Contact contact = new Contact();
        contact.setId(cursor.getLong(0));
        contact.setName(cursor.getString(1));
        return contact;
    }
}
