package com.example.sharedexprences;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class AddNewEventActivity extends AppCompatActivity {

    private static final String TAG = "AddNewGEactivity";

    DatabaseHelper myDb;
    ListView mListView;
    ArrayList<UserDataInNewGElist> fullUserList;
    ArrayList<UserDataInNewGElist> userList;
    ArrayList<UserDataInNewGElist> filteredUserList;
    EditText name, totalSum, description;
    SearchView search;
    UserListAdapterForNewGE adapter;
    Button addNewEventBTN;
    Integer geID, userID;
    boolean is_filtered=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_event_layout);
        mListView = (ListView)findViewById(R.id.ListViewID);
        userList = new ArrayList<>();
        fullUserList = new ArrayList<>();
        filteredUserList = new ArrayList<>();
        myDb = new DatabaseHelper(AddNewEventActivity.this);

        name = (EditText)findViewById(R.id.nameID);
        totalSum = (EditText)findViewById(R.id.sumID);
        description = (EditText)findViewById(R.id.descriptionID);
        search = (SearchView)findViewById(R.id.searchID);
        addNewEventBTN = (Button)findViewById(R.id.addNewGEbtnID);

        geID = Integer.parseInt(getIntent().getStringExtra("GLOBAL_EVENT_ID"));
        userID = Integer.parseInt(getIntent().getStringExtra("LOCAL_USER_ID"));


        displayUsers();
        mListViewListener();
        searchBarListener();
        addNewEventBTNlistener();
    }

    void displayUsers(){
        Cursor cursor = myDb.returnUsersForCertainGE(geID);
        Cursor LUcursor = myDb.returnCursorWithLUData();
        Cursor buffUser;
        LUcursor.moveToNext();
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                if(Integer.parseInt(cursor.getString(2)) != Integer.parseInt(LUcursor.getString(0))){
                    buffUser=myDb.returnCertainUser(Integer.parseInt(cursor.getString(2)));
                    buffUser.moveToNext();
                    userList.add(new UserDataInNewGElist(
                            Integer.parseInt(buffUser.getString(0)),
                            buffUser.getString(3),
                            buffUser.getString(4)
                    ));
                }
            }
            fullUserList = userList;
            adapter = new UserListAdapterForNewGE(
                    AddNewEventActivity.this, R.layout.users_layout_in_new_ge_v1, userList
                    );
            mListView.setAdapter(adapter);
        }
    }
    void mListViewListener(){
        mListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDataInNewGElist user;
                if(is_filtered){
                    user = filteredUserList.get(position);
                }else {
                    user = userList.get(position);
                }
                LinearLayout listElement = (LinearLayout) view;
                if(user.getIs_chosen()==false){
                    listElement.setBackgroundColor(Color.GREEN);
                    user.setIs_chosen(true);
                }else{
                    listElement.setBackgroundColor(Color.WHITE);
                    user.setIs_chosen(false);
                }
            }
        });
    }
    void searchBarListener(){
        search.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filteredUserList.clear();
                        for (UserDataInNewGElist user : userList){
                            if(
                                    user.getSurname().contains(newText) ||
                                    user.getName().contains(newText)
                                ){
                                    filteredUserList.add(user);
                            }
                        }
                        if(filteredUserList.size()==userList.size()){
                            is_filtered=false;
                        }else{
                            is_filtered=true;
                        }
                        adapter = new UserListAdapterForNewGE(
                                AddNewEventActivity.this, R.layout.users_layout_in_new_ge_v1, filteredUserList);
                        mListView.setAdapter(adapter);
                        return false;
                    }
                }
        );
    }
    void addNewEventBTNlistener(){
        addNewEventBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ToastText = "";
                        if (name.getText().toString().equals("")) {
                            Toast.makeText(AddNewEventActivity.this,
                                    "Please add event name", Toast.LENGTH_SHORT).show();
                        }else{

                            if(totalSum.getText().toString().equals("")){
                                Toast.makeText(AddNewEventActivity.this,
                                        "Please add event sum", Toast.LENGTH_SHORT).show();
                            }else{
                                String nameText, descriptionText;
                                Cursor LUcursor = myDb.returnCursorWithLUData();
                                LUcursor.moveToNext();
                                UserDataInNewGElist LUuser = new UserDataInNewGElist(
                                        Integer.parseInt(LUcursor.getString(0)),
                                        LUcursor.getString(1),
                                        LUcursor.getString(2)
                                );
                                ArrayList<Integer> participantsID = new ArrayList<>();
                                for (UserDataInNewGElist user : fullUserList) {
                                    if (user.getIs_chosen() == true) {
                                        participantsID.add(user.getID());
                                    }
                                }
                                if (description.getText().toString().equals("")) {
                                    descriptionText = "no description";
                                } else {
                                    descriptionText = description.getText().toString();
                                }
                                nameText = name.getText().toString();
                                Integer ammountOfParticipants = participantsID.size() + 1;
                                Integer sum = Integer.parseInt(totalSum.getText().toString());
                                float sumPerParticipant = (float)sum / ammountOfParticipants;

                                if (myDb.addNewEvent(nameText, descriptionText, ammountOfParticipants, sum)) {
                                    Log.d(TAG, "Event table - OK");
                                } else {
                                    Log.d(TAG, "Event table - NOT OK");
                                }
                                Cursor cursor = myDb.returnEventsDB();
                                cursor.moveToLast();
                                Integer newEventID = Integer.parseInt(cursor.getString(0));
                                for (Integer userID : participantsID) {
                                    if (myDb.addNewRecordToEUdb(newEventID, userID, false) == false) {
                                        Toast.makeText(AddNewEventActivity.this,
                                                "Something went wrong during EU fill", Toast.LENGTH_LONG);
                                        break;
                                    }
                                }
                                Log.d(TAG, "EU participants insertion - OK");
                                Integer adminID = LUuser.getID();
                                myDb.addNewRecordToEUdb(newEventID, adminID, true);
                                Log.d(TAG, "EU admin insertion - OK");

                                for (Integer userID : participantsID) {
                                    if (userID!=adminID) {
                                        if(myDb.addNewRecordToUUdb(newEventID, adminID, userID, sumPerParticipant)==false) {
                                            Log.d(TAG, "UU db - FAILED");
                                            break;
                                        }
                                    }
                                }
                                Log.d(TAG, "UU db - OK");

                                if (myDb.addNewRecordToGEEdb(geID, newEventID)) {
                                    Log.d(TAG, "new Event in GEE - OK");
                                } else {
                                    Log.d(TAG, "new Event in GEE - NOT OK");
                                }

                                Log.d(TAG, "Event creation - OK");
                                Intent intent = new Intent(AddNewEventActivity.this, GlobalEventPage.class);
                                intent.putExtra("GLOBAL_EVENT_ID", geID.toString());
                                intent.putExtra("LOCAL_USER_ID", userID.toString());
                                startActivity(intent);
                            }
                        }
                    }
                }
        );
    }
}
