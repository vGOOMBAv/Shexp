package com.example.sharedexprences;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GlobalEventPage extends AppCompatActivity {

    private static final String TAG = "GlobalEventPage";
    DatabaseHelper myDb;
    ImageView deleteImage, usersImage, leaveGE, addNewEvent;
    TextView geName, sum, noLocalEventsYet, freeSpace;
    ListView mListView;

    ArrayList<Integer> eventsOfCurrentGEids;
    ArrayList<EventDataInGElist> eventsOfCurrentGE;

    Integer geID, userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ge_page);

        myDb = new DatabaseHelper(GlobalEventPage.this);

        geName = (TextView)findViewById(R.id.geNameID);
        sum = (TextView)findViewById(R.id.sumID);
        noLocalEventsYet = (TextView)findViewById(R.id.noLocalEventsYetID);
        freeSpace = (TextView)findViewById(R.id.freeSpaceID);
        deleteImage = (ImageView)findViewById(R.id.deleteID);
        usersImage = (ImageView)findViewById(R.id.usersID);
        leaveGE = (ImageView)findViewById(R.id.leaveGEid);
        addNewEvent = (ImageView)findViewById(R.id.addNewEventID);
        mListView = (ListView)findViewById(R.id.eventLvID);
        eventsOfCurrentGEids = new ArrayList<>();
        eventsOfCurrentGE = new ArrayList<>();

        geID = Integer.parseInt(getIntent().getStringExtra("GLOBAL_EVENT_ID"));
        userID = Integer.parseInt(getIntent().getStringExtra("LOCAL_USER_ID"));

        showInterface();
        addNewEventBTNlistener();
        mListViewListener();
        deleteImageListener();
    }

    void showInterface(){

        if(myDb.is_GEadmin(geID, userID)){
            deleteImage.setVisibility(View.VISIBLE);
        }else{
            deleteImage.setVisibility(View.GONE);
        }
        Cursor cursor = myDb.returnCertainGE(geID);
        cursor.moveToNext();
        geName.setText(cursor.getString(1));
        if(myDb.is_geEmpty(geID)){
            noLocalEventsYet.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            freeSpace.setVisibility(View.GONE);
        }else{
            noLocalEventsYet.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            freeSpace.setVisibility(View.VISIBLE);

            cursor = myDb.returnEventsForCertainGE(geID);
            while (cursor.moveToNext()){
                eventsOfCurrentGEids.add(Integer.parseInt(cursor.getString(1)));
            }
            Cursor buffEvent;
            for(Integer geID : eventsOfCurrentGEids){
                buffEvent = myDb.returnCertainEvent(geID);
                buffEvent.moveToNext();
                eventsOfCurrentGE.add(new EventDataInGElist(
                        Integer.parseInt(buffEvent.getString(0)),
                        buffEvent.getString(1),
                        Integer.parseInt(buffEvent.getString(3))
                ));
            }
            EventAdapterForGePage adapter = new EventAdapterForGePage(
                    GlobalEventPage.this, R.layout.ge_event_listview_layout, eventsOfCurrentGE
            );
            mListView.setAdapter(adapter);

        }
    }

    void addNewEventBTNlistener(){
        addNewEvent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GlobalEventPage.this, AddNewEventActivity.class);
                        intent.putExtra("GLOBAL_EVENT_ID", geID.toString());
                        intent.putExtra("LOCAL_USER_ID", userID.toString());
                        startActivity(intent);
                    }
                }
        );
    }

    void mListViewListener() {
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        EventDataInGElist buffEvent = eventsOfCurrentGE.get(position);
                        Intent intent = new Intent(GlobalEventPage.this, EventPage.class);
                        intent.putExtra("EVENT_ID", buffEvent.getEventID().toString());
                        intent.putExtra("GLOBAL_EVENT_ID", geID.toString());
                        intent.putExtra("LOCAL_USER_ID", userID.toString());
                        startActivity(intent);
                    }
                });
    }

    void deleteImageListener(){
        deleteImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean is_deleted = myDb.deleteGE(geID);
                        if(is_deleted) {
                            Log.d(TAG, "GE deletion succesfull");
                        }else{
                            Log.d(TAG, "GE deletion failed");
                        }
                        Intent intent = new Intent(GlobalEventPage.this, UserPage.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
