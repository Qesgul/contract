package org.nupter.contract;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import java.util.Map;

/**
 * Created by Axes on 2016/2/1.
 */
public class People extends Fragment {
    private ListView listview;
    private ImageView add;
    private SimpleAdapter adapter;
    private Cursor result;
    private List<Map<String, Object>> allContacts;

    ImageButton ewm;
    ImageButton personal;
    ImageButton search;

    private LinearLayout people;
    private LinearLayout phone;
    private ImageView peopleimg;
    private ImageView phoneimg;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.people, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



}