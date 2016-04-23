package com.example.administrateur.geriat3000;

import java.sql.Timestamp;

/**
 * Created by quentin on 23/04/16.
 */
public class MessageDb {
    private long id;
    private long contactId;
    private String message;
    private int left;

    public long getId() {return this.id;}

    public void setId(long id) {this.id = id;}

    public long getContactId() {return this.contactId;}

    public void setContactId(long contactId) {this.contactId = contactId;}

    public String getMessage() {return this.message;}

    public void setMessage(String message) {this.message = message;}

    public int getLeft() {return this.left;}

    public void setLeft(int left) {this.left = left;}
}
