package com.example.sharedexprences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.annotation.Target;

public class DebugActivity extends AppCompatActivity {

    private static final String TAG = "DebugActivity";
    DatabaseHelper myDb;
    Button clearAllTablesBTN, addTestUsersBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_layout);

        myDb = new DatabaseHelper(DebugActivity.this);
        clearAllTablesBTN = (Button)findViewById(R.id.clearAllTablesID);
        addTestUsersBTN = (Button)findViewById(R.id.addTestUsersID);

        clearAllTablesBTNlistener();
        addTestUsersBTNlistener();
    }

    void clearAllTablesBTNlistener(){
        clearAllTablesBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.clearAllTables();
                    }
                }
        );
    }
    void addTestUsersBTNlistener(){
        addTestUsersBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.addUser("test0", "1", "Puppet", "0");
                        myDb.addUser("test1", "1", "Puppet", "1");
                        myDb.addUser("test2", "1", "Puppet", "2");
                        myDb.addUser("test3", "1", "Puppet", "3");
                        myDb.addUser("test4", "1", "Puppet", "4");
                        myDb.addUser("test5", "1", "Puppet", "5");
                        myDb.addUser("test6", "1", "Puppet", "6");
                        myDb.addUser("test7", "1", "Puppet", "7");
                        myDb.addUser("test8", "1", "Puppet", "8");
                        myDb.addUser("test9", "1", "Puppet", "9");
                    }
                }
        );
    }
}
