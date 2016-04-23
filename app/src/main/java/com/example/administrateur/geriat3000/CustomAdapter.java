package com.example.administrateur.geriat3000;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by quentin on 23/04/16.
 */
public class CustomAdapter extends BaseAdapter {

    List<Contact> result;
    Context context;
    private static LayoutInflater inflater=null;
    private ContactsDataSource dataSource;

    public CustomAdapter(MainActivity mainActivity, List<Contact> contacts) {
        result=contacts;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return result.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        dataSource = new ContactsDataSource(context);
        dataSource.open();


        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.custom_layout, null);
        TextView lblExpenseCancel = (TextView)view.findViewById(R.id.lblExpenseCancel);
        lblExpenseCancel.setText(result.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+result.get(position).getName(), Toast.LENGTH_LONG).show();
                dataSource.deleteContact(dataSource.getAllContacts().get(position));
            }
        });
        return view;
    }
}
