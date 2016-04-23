package com.example.administrateur.geriat3000;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quentin on 24/04/16.
 */
public class ChatArrayAdapter extends BaseAdapter {

    ArrayAdapter<MessageDb> result;
    Context context;
    private static LayoutInflater inflater=null;
    private MessagesDataSource dataSource;
    private long contact_Id;
    private TextView chatText;

    public ChatArrayAdapter(Message message, ArrayAdapter<MessageDb> messages, long contactId) {
        result=messages;
        context=message;
        contact_Id = contactId;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        System.out.println("total: " + result.getCount());
        return result.getCount();
    }

    @Override
    public Object getItem(int position) {
        return result.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return result.getItemId(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        dataSource = new MessagesDataSource(context);
        dataSource.open();

        View view = convertView;

        MessageDb message = dataSource.getAllMessagesWithContactId(contact_Id).get(position);

        if (message.getLeft() == 1) {
            view = inflater.inflate(R.layout.list_row_layout_even, null);
        }else{
            view = inflater.inflate(R.layout.list_row_layout_odd, null);
        }
        chatText = (TextView) view.findViewById(R.id.msgr);
        System.out.println("coucou: " + message.getMessage());
        chatText.setText(message.getMessage());
        return view;
    }

    /*



    public View getView(int position, View convertView, ViewGroup parent) {
        MessageDb chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.getLeft() == 1) {
            row = inflater.inflate(R.layout.list_row_layout_even, parent, false);
        }else{
            row = inflater.inflate(R.layout.list_row_layout_odd, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.msgr);
        chatText.setText(chatMessageObj.getMessage());
        return row;
    }

    */
}
