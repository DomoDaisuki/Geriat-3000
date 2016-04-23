package com.example.administrateur.geriat3000;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactsDataSource dataSource;
    private Toolbar toolbar;
    private ListView listView;
    private FloatingActionButton button;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listContacts);

        button = (FloatingActionButton) findViewById(R.id.addButton);


        dataSource = new ContactsDataSource(this);
        dataSource.open();

        List<Contact> values = dataSource.getAllContacts();


        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, values);

        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        contact1.setName("mathieu");
        contact1.setId(0);
        adapter.add(contact1);
        contact2.setName("quentin");
        contact2.setId(1);
        adapter.add(contact2);
        System.out.println("truc " + adapter.toString());
        Log.d("CREATION", "truc " + adapter.toString());
        Log.d("CREATION", "truc " + adapter.getCount());
        Log.d("CREATION", "truc " + adapter.getItem(0));
        Log.d("CREATION", "truc " + adapter.getItem(1));
        //listView.setAdapter(adapter);
        values.set(0, contact1);
        values.set(1, contact2);
        listView.setAdapter(new CustomAdapter(this, values));

        listView.setOnItemClickListener(onItemClickListener);




        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_layout_add_contact);

                Button dialogButtonOk = (Button) dialog.findViewById(R.id.dialogButtonOK);
                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
                // if button is clicked, close the custom dialog
                 dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText nameContact = (EditText) dialog.findViewById(R.id.nameContact);
                        EditText phoneContact = (EditText) dialog.findViewById(R.id.phoneContact);
                        dataSource.createContact(nameContact.getText().toString(), phoneContact.getText().toString());
                    }
                });

                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Message.class);
        startActivity(intent);
    }

    public void deleteContact(int position, View view) {
        //dataSource.deleteContact(position);
    }


    @Override
    public void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        dataSource.close();
        super.onPause();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            Toast.makeText(context, "coucou", Toast.LENGTH_LONG).show();
        }
    };
}
