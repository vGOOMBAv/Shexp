package com.example.sharedexprences;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Dictionary;

public class UserPage extends AppCompatActivity {

    DatabaseHelper myDb;

    ImageView settings, profile, logout, addNewElement, finances;
    TextView debt, nameSurname, findSomethinToDo, freeSpace;
    Integer userID;
    ListView mListView;
    ArrayList<Integer> localUserGEids;
    ArrayList<GEdataInUserPage> localUserGEs;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(UserPage.this);
        builder.setTitle("Quit?");
        builder.setMessage("Do you want to quit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        */
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);
        myDb = new DatabaseHelper(UserPage.this);

        settings = (ImageView)findViewById(R.id.settingID);
        profile = (ImageView)findViewById(R.id.profileID);
        logout = (ImageView)findViewById(R.id.logoutID);
        debt = (TextView)findViewById(R.id.debtSizeID);
        freeSpace = (TextView)findViewById(R.id.freeSpaceID);
        nameSurname = (TextView)findViewById(R.id.nameSurnameID);
        findSomethinToDo = (TextView)findViewById(R.id.findSomethinToDoID);
        addNewElement = (ImageView)findViewById(R.id.addNewGEid);
        finances = (ImageView)findViewById(R.id.financesID);
        mListView = (ListView)findViewById(R.id.geLvID);
        localUserGEids = new ArrayList<>();
        localUserGEs = new ArrayList<>();

        userDataGetter();
        logoutListener();
        addNewElementListener();
        mListViewListener();
        showInterface();
        financesListener();
    }

    public void userDataGetter(){
        Cursor cursor = myDb.returnCursorWithLUData();
        String name, surname;
        cursor.moveToNext();
        userID = Integer.parseInt(cursor.getString(0));
        name = cursor.getString(1);
        surname = cursor.getString(2);
        nameSurname.setText(name + " " + surname);
        float debtValue = countDebt();
        if(debtValue==0){
            debt.setText("nothing");
        }else{
            debt.setText(Float.toString(debtValue));
        }
    }
    void logoutListener(){
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.luTableCleaning();
                        Intent intent = new Intent(UserPage.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    void addNewElementListener(){
        addNewElement.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserPage.this, AddNewGEactivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    void showInterface(){
        if(myDb.is_inGE(userID)==false){
                findSomethinToDo.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
                freeSpace.setVisibility(View.GONE);
        }else{
            findSomethinToDo.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            freeSpace.setVisibility(View.VISIBLE);


            Cursor cursor = myDb.returnGEdbForCertainUser(userID);
            while (cursor.moveToNext()){
                localUserGEids.add(Integer.parseInt(cursor.getString(1)));
            }
            Cursor buffGE;
            for(Integer geID : localUserGEids){
                buffGE = myDb.returnCertainGE(geID);
                buffGE.moveToNext();
                localUserGEs.add(new GEdataInUserPage(
                        Integer.parseInt(buffGE.getString(0)),
                        buffGE.getString(1)
                ));
            }
            GEAdapterForUserPage adapter = new GEAdapterForUserPage(
                    UserPage.this, R.layout.userpage_ge_listview_layout, localUserGEs
            );
            mListView.setAdapter(adapter);

        }
    }
    void mListViewListener() {
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        GEdataInUserPage buffGE = localUserGEs.get(position);
                        Intent intent = new Intent(UserPage.this, GlobalEventPage.class);
                        intent.putExtra("GLOBAL_EVENT_ID", buffGE.getGeID().toString());
                        intent.putExtra("LOCAL_USER_ID", userID.toString());
                        startActivity(intent);
                    }
                });
    }

    float countDebt(){
        Cursor debts = myDb.returnAllDebtsForCertainUser(userID);
        if(debts.getCount()==0) {
            return 0;
        }else{
            float totalSum=0;
            while(debts.moveToNext()){
                totalSum+=Float.parseFloat(debts.getString(4));
            }
            return totalSum;
        }
    }

    void financesListener(){
        finances.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        intent = new Intent(UserPage.this, FinancePage.class);
                        intent.putExtra("LOCAL_USER_ID", userID.toString());

                        startActivity(intent);
                    }
                }
        );
    }
}
