package com.example.administrateur.geriat3000;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by quentin on 24/04/16.
 */
public class CustomAdapterPreference extends BaseAdapter {

    ArrayAdapter<Prefere> result;
    Context context;
    private static LayoutInflater inflater=null;
    private PreferesDataSource dataSource;
    private TextView preftxt;

    public CustomAdapterPreference(Message messageActivity, ArrayAdapter<Prefere> preferes) {
        result=preferes;
        context=messageActivity;
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
        dataSource = new PreferesDataSource(context);
        dataSource.open();

        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.custom_layout_item_preference, null);

        Prefere prefere = dataSource.getAllPreferes().get(position);


        preftxt = (TextView) view.findViewById(R.id.item_preference);
        preftxt.setText(prefere.getMessage());

        return view;

    }
}
