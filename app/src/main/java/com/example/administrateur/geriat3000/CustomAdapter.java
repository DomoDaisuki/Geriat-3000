package com.example.administrateur.geriat3000;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by quentin on 23/04/16.
 */
public class CustomAdapter extends BaseAdapter {

    ArrayAdapter<Contact> result;
    Context context;
    private static LayoutInflater inflater=null;
    private ContactsDataSource dataSource;
    private ListView listView;

    public CustomAdapter(MainActivity mainActivity, ArrayAdapter<Contact> contacts) {
        result=contacts;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
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


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        dataSource = new ContactsDataSource(context);
        dataSource.open();


        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.custom_layout, null);

        View test = inflater.inflate(R.layout.content_main, null);

        listView = (ListView) test.findViewById(R.id.listContacts);
        listView.setAdapter(result);

        TextView lblExpenseCancel = (TextView)view.findViewById(R.id.lblExpenseCancel);
        lblExpenseCancel.setText(result.getItem(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+result.getItem(position).getName() + " "
                        + result.getItem(position).getId()
                        + " " + result.getItem(position).getPhone_number(), Toast.LENGTH_LONG).show();
                dataSource.deleteContact(dataSource.getAllContacts().get(position));
                result.remove(result.getItem(position));
                notifyDataSetChanged();
            }
        });
        notifyDataSetChanged();
        return view;
    }
}
