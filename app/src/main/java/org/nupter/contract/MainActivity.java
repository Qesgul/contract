package org.nupter.contract;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private ListView listview;
    private LinearLayout add;
    private SimpleAdapter adapter;
    private Cursor result;
    private List<Map<String, Object>> allContacts;

    ImageButton ewm;
    ImageButton personal;
    ImageButton search;
    ImageButton phone233;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        ewm = (ImageButton) findViewById(R.id.ewm);
        personal = (ImageButton) findViewById(R.id.personal);
        search = (ImageButton) findViewById(R.id.search);
        add = (LinearLayout) super.findViewById(R.id.add);
        ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ewm.class);
                startActivity(intent);
            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, personal.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, add_page.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, search.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.phone233);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Phone.class));
            }
        });


        listview = (ListView) super.findViewById(R.id.listview);

        // 得到联系人数据放入链表中
        result = super.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        allContacts = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put("contact_id", result.getInt(result.getColumnIndex(ContactsContract.Contacts._ID)));
            map.put("contact_name", result.getString(result.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            allContacts.add(map);
        }
        adapter = new SimpleAdapter(this, allContacts, R.layout.contact_item, new String[]{
                "contact_id",
                "contact_name"
        }, new int[]{
                R.id.contact_id,
                R.id.contact_name
        });
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // 通过id得到号码，调到详情界面
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                long contactsId = Long.parseLong(allContacts.get(position).get("contact_id").toString());
                String name = allContacts.get(position).get("contact_name").toString();

                String number = "";
                String phoneSelection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
                String[] phoneSelectionArgs = {
                        String.valueOf(contactsId)
                };
                Cursor c = MainActivity.this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, phoneSelection, phoneSelectionArgs, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    number = number + c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                Intent intent = new Intent(MainActivity.this, ContactsEdit.class);
                intent.putExtra("id", contactsId);
                intent.putExtra("name", name);
                intent.putExtra("number", number);
                startActivity(intent);
            }

        });


    }
}
