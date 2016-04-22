package com.example.administrateur.geriat3000;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactsDataSource dataSource;
    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listContacts);


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
}
