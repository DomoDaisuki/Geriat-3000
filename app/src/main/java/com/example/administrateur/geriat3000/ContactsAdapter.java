package com.example.administrateur.geriat3000;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by quentin on 23/04/16.
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {

    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_main, parent, false);
        }

        TextView lblExpenseCancel = (TextView) convertView.findViewById(R.id.lblExpenseCancel);

        lblExpenseCancel.setText(contact.getName());
        return convertView;
    }
}
