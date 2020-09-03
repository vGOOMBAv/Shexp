package com.example.sharedexprences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DatabaseHelper myDb;
    EditText login, password;
    Button signIN, signUP;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(MainActivity.this);

        login = (EditText)findViewById(R.id.loginID);
        password = (EditText)findViewById(R.id.passwordID);
        signIN = (Button)findViewById(R.id.signInID);
        signUP = (Button)findViewById(R.id.signUpID);

        is_logged();

        signUpButtonListener();
        signInButtonListener();
    }

    void signUpButtonListener(){
        signUP.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(MainActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    void signInButtonListener(){
        signIN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(
                                !login.getText().toString().equals("") &&
                                !password.getText().toString().equals("")
                        ) {
                            if(
                                    login.getText().toString().equals("Admin") &&
                                    password.getText().toString().equals("111111")
                            ){
                                Intent intent = new Intent(MainActivity.this, DebugActivity.class);
                                startActivity(intent);
                            }else {
                                if (myDb.loginAttemptResponce(login.getText().toString(), password.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Login succesfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, UserPage.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "You fucked up", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
        );
    }
    void is_logged(){
        if(myDb.is_logged()){
            Intent intent = new Intent(MainActivity.this, UserPage.class);
            startActivity(intent);
        }
    }
}
