package com.example.administrateur.geriat3000;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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

        ImageButton buttonDelete = (ImageButton)view.findViewById(R.id.DeleteContact);
        buttonDelete.setTag(position);
        buttonDelete.setOnClickListener(buttonDeleteClickListener);

        ImageButton buttonPhone = (ImageButton) view.findViewById(R.id.Tel2);
        buttonPhone.setTag(position);
        buttonPhone.setOnClickListener(buttonPhoneClickListener);

        TextView lblExpenseCancel = (TextView)view.findViewById(R.id.lblExpenseCancel);
        lblExpenseCancel.setText(result.getItem(position).getName());

        return view;
    }

    private View.OnClickListener buttonDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(context, "You Clicked "+result.getItem((Integer) v.getTag()).getName() + " "
                    + result.getItem((Integer) v.getTag()).getId()
                    + " " + result.getItem((Integer) v.getTag()).getPhone_number(), Toast.LENGTH_LONG).show();
            dataSource.deleteContact(dataSource.getAllContacts().get((Integer) v.getTag()));
            result.remove(result.getItem((Integer) v.getTag()));
            notifyDataSetChanged();
        }
    };

    private View.OnClickListener buttonPhoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tel = result.getItem((Integer) v.getTag()).getPhone_number();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + tel));
            context.startActivity(callIntent);
        }
    };
}
