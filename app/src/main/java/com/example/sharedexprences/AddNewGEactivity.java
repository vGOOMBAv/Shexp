package com.example.sharedexprences;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class AddNewGEactivity extends AppCompatActivity {

    private static final String TAG = "AddNewGEactivity";

    DatabaseHelper myDb;
    ListView mListView;
    ArrayList<UserDataInNewGElist> fullUserList;
    ArrayList<UserDataInNewGElist> userList;
    ArrayList<UserDataInNewGElist> filteredUserList;
    EditText name, description;
    SearchView search;
    UserListAdapterForNewGE adapter;
    Button addNewGEbtn;
    boolean is_filtered = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_ge_layout);
        mListView = (ListView)findViewById(R.id.ListViewID);
        userList = new ArrayList<>();
        fullUserList = new ArrayList<>();
        filteredUserList = new ArrayList<>();
        myDb = new DatabaseHelper(AddNewGEactivity.this);

        name = (EditText)findViewById(R.id.nameID);
        description = (EditText)findViewById(R.id.descriptionID);
        search = (SearchView)findViewById(R.id.searchID);
        addNewGEbtn = (Button)findViewById(R.id.addNewGEbtnID);

        displayUsers();
        mListViewListener();
        searchBarListener();
        addNewGEbtnListener();
    }

    void displayUsers(){
        Cursor cursor = myDb.returnUsersDB();
        Cursor LUcursor = myDb.returnCursorWithLUData();
        LUcursor.moveToNext();
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                if(Integer.parseInt(cursor.getString(0)) != Integer.parseInt(LUcursor.getString(0))){
                    userList.add(new UserDataInNewGElist(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(3),
                            cursor.getString(4)
                    ));
                }
            }
            fullUserList = userList;
            adapter = new UserListAdapterForNewGE(
                    AddNewGEactivity.this, R.layout.users_layout_in_new_ge_v1, userList
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
                    Toast.makeText(AddNewGEactivity.this, "ID: "+user.getID(),
                            Toast.LENGTH_SHORT).show();
                }else{
                    listElement.setBackgroundColor(Color.WHITE);
                    user.setIs_chosen(false);
                    Toast.makeText(AddNewGEactivity.this, "ID: "+user.getID(),
                            Toast.LENGTH_SHORT).show();
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
                                ) {
                                filteredUserList.add(user);
                                }
                            }
                        if(filteredUserList.size()==userList.size()){
                            is_filtered=false;
                        }else{
                            is_filtered=true;
                        }
                        adapter = new UserListAdapterForNewGE(
                                    AddNewGEactivity.this, R.layout.users_layout_in_new_ge_v1, filteredUserList);
                        mListView.setAdapter(adapter);
                        return false;
                    }
                }
        );
    }
    void addNewGEbtnListener(){
        addNewGEbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ToastText = "";
                        if(name.getText().toString().equals("")){
                            Toast.makeText(AddNewGEactivity.this,
                                    "Please add event name", Toast.LENGTH_SHORT).show();
                        }else {
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
                            nameText=name.getText().toString();
                            Integer ammountOfParticipants = participantsID.size() + 1;
                            Integer sum = 0;

                            if(myDb.addNewGE(nameText, descriptionText, ammountOfParticipants, sum)){
                                Log.d(TAG, "GE creation - OK");
                            }else{
                                Log.d(TAG, "GE creation - NOT OK");
                            }
                            Cursor cursor = myDb.returnGEdb();
                            cursor.moveToLast();
                            Integer newGEid = Integer.parseInt(cursor.getString(0));
                            for (Integer userID : participantsID){
                                if(myDb.addNewRecordToGEUdb(newGEid, userID, false)==false){
                                    Toast.makeText(AddNewGEactivity.this,
                                            "Something went wrong during GEU fill", Toast.LENGTH_LONG);
                                    break;
                                }
                            }
                            Log.d(TAG, "GEU participants insertion - OK");
                            myDb.addNewRecordToGEUdb(newGEid, LUuser.getID(), true);
                            Log.d(TAG, "GEU admin insertion - OK");

                            Log.d(TAG, "GE creation - OK");
                            Intent intent = new Intent(AddNewGEactivity.this, UserPage.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
}
