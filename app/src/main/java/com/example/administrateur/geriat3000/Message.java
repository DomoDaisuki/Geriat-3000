package com.example.administrateur.geriat3000;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Message extends AppCompatActivity {

    private StatusTracker   mStatusTracker = StatusTracker.getInstance();
    private String          mActivityName;
    private TextView        mStatusView;
    private TextView        mStatusAllView;

    private MessagesDataSource dataSource;
    private PreferesDataSource dataSourcePreference;


    private static final String TAG = "ChatActivity";

    private ListView listViewMessage;
    private EditText chatText;
    private ImageButton buttonSend;
    private FloatingActionButton button;
    private int side = 1;
    private long contactId;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dataSource = new MessagesDataSource(this);
        dataSource.open();

        listViewMessage = (ListView) findViewById(R.id.listMessage);
        button = (FloatingActionButton) findViewById(R.id.preferenceButton);

        Intent intent = getIntent();
        contactId = Long.parseLong(intent.getStringExtra("contactId"));

        final List<MessageDb> values = dataSource.getAllMessagesWithContactId(contactId);

        final ArrayAdapter<MessageDb> adapterMessage = new ArrayAdapter<MessageDb>(this, android.R.layout.simple_list_item_1, values);

        System.out.println("kirikou: " + contactId);


        listViewMessage.setAdapter(new ChatArrayAdapter(this, adapterMessage, contactId));


        buttonSend = (ImageButton) findViewById(R.id.sendButton);


        chatText = (EditText) findViewById(R.id.myMessage);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage(adapterMessage);
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage(adapterMessage);
            }
        });

        listViewMessage.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        // listViewMessage.setAdapter(adapter);

        //to scroll the list view to bottom on data change
        adapterMessage.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listViewMessage.setSelection(adapterMessage.getCount() - 1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            ListView listViewPreference;

            @Override
            public void onClick(View arg0) {

                dataSourcePreference = new PreferesDataSource(context);
                dataSourcePreference.open();

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_layout_preference);

                listViewPreference = (ListView) dialog.findViewById(R.id.list_view_preference);
                Button dialogButtonAdd = (Button) dialog.findViewById(R.id.buttonAddPreference);

                List<Prefere> preValues = dataSourcePreference.getAllPreferes();

                final ArrayAdapter<Prefere> adapterPreference = new ArrayAdapter<Prefere>(context, android.R.layout.simple_list_item_1, preValues);

                System.out.println("coucou: " + adapterPreference.getItem(0).getMessage());



                listViewPreference.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

                listViewPreference.setAdapter(new CustomAdapterPreference(Message.this, adapterPreference));



                listViewPreference.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Object o = listViewPreference.getItemAtPosition(position);
                        Prefere prefere = (Prefere)o;
                        System.out.println("a que coucou: " + prefere.getMessage());

                        dialog.dismiss();
                        chatText.setText(prefere.getMessage());
                    }
                });



                adapterPreference.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        listViewMessage.setSelection(adapterPreference.getCount() - 1);
                    }
                });

                dialogButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText prefMessage = (EditText) dialog.findViewById(R.id.editTextPreference);
                        dataSourcePreference.createPrefere(prefMessage.getText().toString());

                        Prefere prefere = new Prefere();
                        prefere.setMessage(prefMessage.getText().toString());
                        adapterPreference.add(prefere);
                        adapterPreference.notifyDataSetChanged();

                        prefMessage.setText("");
                    }
                });

                dialog.show();
            }

        });
    }

    private boolean sendChatMessage(final ArrayAdapter<MessageDb> adapter) {
        MessageDb messageDb = new MessageDb();
        messageDb.setContactId(contactId);
        messageDb.setMessage(chatText.getText().toString());
        messageDb.setLeft(side);

        dataSource.createMessage(messageDb.getContactId(), messageDb.getMessage(), messageDb.getLeft());
        System.out.println("contactId: " + messageDb.getContactId() + " message: " + messageDb.getMessage() + " left: " + messageDb.getLeft());
        adapter.add(messageDb);
        adapter.notifyDataSetChanged();
        chatText.setText("");
        if (side == 1)
            side = 0;
        else
            side = 1;
        return true;
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_restart));
        Utils.printStatus(mStatusView, mStatusAllView);
    }
*/
    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
  //      mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
    //    Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
     //   mStatusTracker.setStatus(mActivityName, getString(R.string.on_pause));
       // Utils.printStatus(mStatusView, mStatusAllView);
    }
/*
    @Override
    protected void onStop() {
        super.onStop();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_stop));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_destroy));
        mStatusTracker.clear();
    }
*/
}
