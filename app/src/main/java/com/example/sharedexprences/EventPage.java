package com.example.sharedexprences;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EventPage extends AppCompatActivity {

    private static final String TAG = "EventPage";
    TextView eventName, sum, description;
    EditText eventNameSetup, sumSetup, descriptionSetup;
    LinearLayout eventNameSetupLL;
    ListView mListView;
    ArrayList<UserDataInNewGElist> participants;
    Button updateEventBTN, deleteBTN;

    DatabaseHelper myDb;

    Integer userID, eventID, geID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);

        myDb = new DatabaseHelper(EventPage.this);
        eventName = (TextView)findViewById(R.id.eventNameID);
        sum = (TextView)findViewById(R.id.textViewSumID);
        description = (TextView)findViewById(R.id.textViewDescriptionID);
        eventNameSetup = (EditText)findViewById(R.id.editTextNameID);
        sumSetup = (EditText)findViewById(R.id.editTextSumID);
        descriptionSetup = (EditText)findViewById(R.id.editTextDescriptionID);
        eventNameSetupLL = (LinearLayout)findViewById(R.id.eventNameSetupLLID);
        mListView = (ListView)findViewById(R.id.lvID);
        updateEventBTN = (Button)findViewById(R.id.updateBTNid);
        deleteBTN = (Button)findViewById(R.id.deleteBTNid);

        userID = Integer.parseInt(getIntent().getStringExtra("LOCAL_USER_ID"));
        eventID = Integer.parseInt(getIntent().getStringExtra("EVENT_ID"));
        geID = Integer.parseInt(getIntent().getStringExtra("GLOBAL_EVENT_ID"));

        participants = new ArrayList<>();

        showInterface();
        deleteBTNlistener();
        updateEventBTNlistener();
    }

    void showInterface(){
        Cursor event = myDb.returnCertainEvent(eventID);
        event.moveToNext();
        if(myDb.is_EventAdmin(eventID, userID)){
            eventNameSetupLL.setVisibility(View.VISIBLE);
            eventNameSetup.setText(event.getString(1));
            description.setVisibility(View.GONE);
            descriptionSetup.setVisibility(View.VISIBLE);
            descriptionSetup.setText(event.getString(2));
            sum.setVisibility(View.GONE);
            sumSetup.setVisibility(View.VISIBLE);
            sumSetup.setText(event.getString(3));
            updateEventBTN.setVisibility(View.VISIBLE);
            deleteBTN.setVisibility(View.VISIBLE);
        }else{
            eventNameSetupLL.setVisibility(View.GONE);
            descriptionSetup.setVisibility(View.GONE);
            description.setVisibility(View.VISIBLE);
            description.setText(event.getString(2));
            sumSetup.setVisibility(View.GONE);
            sum.setVisibility(View.VISIBLE);
            sum.setText(event.getString(3));
            updateEventBTN.setVisibility(View.GONE);
            deleteBTN.setVisibility(View.GONE);
        }
        Cursor users = myDb.returnUsersForCertainEvent(eventID);
        Integer idOfBuffUser;
        Cursor buffUser;
        while(users.moveToNext()){
            idOfBuffUser=Integer.parseInt(users.getString(2));
            buffUser=myDb.returnCertainUser(idOfBuffUser);
            buffUser.moveToNext();
            participants.add(
                    new UserDataInNewGElist(
                        Integer.parseInt(buffUser.getString(0)),
                        buffUser.getString(3),
                        buffUser.getString(4)
                    )
            );
            ArrayAdapter adapter = new UserListAdapterForNewGE(
                    EventPage.this, R.layout.users_layout_in_new_ge_v1, participants
            );
            mListView.setAdapter(adapter);
        }
    }
    void deleteBTNlistener(){
        deleteBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean is_deleted = myDb.deleteEvent(eventID);
                        if(is_deleted) {
                            Log.d(TAG, "Event deletion succesfull");
                        }else{
                            Log.d(TAG, "Event deletion failed");
                        }
                        Intent intent = new Intent(EventPage.this, GlobalEventPage.class);
                        intent.putExtra("LOCAL_USER_ID", userID.toString());
                        intent.putExtra("GLOBAL_EVENT_ID", geID.toString());
                        startActivity(intent);
                    }
                }
        );
    }
    void updateEventBTNlistener(){
        updateEventBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean is_updated = myDb.updateEvent(
                                eventID, eventNameSetup.getText().toString(),
                                sumSetup.getText().toString(),
                                descriptionSetup.getText().toString());
                                if(is_updated) {
                                    Log.d(TAG, "Event update succesfull");
                                }else{
                                    Log.d(TAG, "Event update failed");
                                }
                                Intent intent = new Intent(EventPage.this, GlobalEventPage.class);
                                intent.putExtra("LOCAL_USER_ID", userID.toString());
                                intent.putExtra("GLOBAL_EVENT_ID", geID.toString());
                                startActivity(intent);
                    }
                }
        );
    }
}
