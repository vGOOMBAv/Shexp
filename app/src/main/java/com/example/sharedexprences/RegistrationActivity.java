package com.example.sharedexprences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private static final String TAG = "RegistrationActivity";
    EditText login, password, confirmPassword, name, surname;
    Button signUP;
    LinearLayout errorMatchLayout;
    TextView errorTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        myDb = new DatabaseHelper(RegistrationActivity.this);

        login = (EditText)findViewById(R.id.loginID);
        password = (EditText)findViewById(R.id.passwordID);
        confirmPassword = (EditText)findViewById(R.id.confirmPasswordID);
        name = (EditText)findViewById(R.id.nameID);
        surname = (EditText)findViewById(R.id.surnameID);
        signUP = (Button)findViewById(R.id.signUpID);
        errorTV = (TextView)findViewById(R.id.errorTVID);

        signUpListener();
    }

    void signUpListener(){
        signUP.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name_check, surname_check, login_check, password_check, confirmPassword_check;
                        password_check=password.getText().toString();
                        confirmPassword_check=confirmPassword.getText().toString();
                        name_check = name.getText().toString();
                        surname_check = surname.getText().toString();
                        login_check = login.getText().toString();
                        if(
                                password_check.equals("") ||
                                login_check.equals("")  ||
                                name_check.equals("") ||
                                surname_check.equals("")
                        ){
                            errorTV.setText("Fill all the fields");
                            errorTV.setVisibility(View.VISIBLE);
                        }else{
                            errorTV.setVisibility(View.GONE);
                            if(!password_check.equals(confirmPassword_check)){
                                errorTV.setText("Passwords don`t match");
                                errorTV.setVisibility(View.VISIBLE);
                            }else{
                                errorTV.setVisibility(View.GONE);
                                if(!myDb.is_loginAvaiable(login_check)){
                                    errorTV.setText("Login already taken");
                                    errorTV.setVisibility(View.VISIBLE);
                                }else{
                                    errorTV.setVisibility(View.GONE);
                                    if (myDb.addUser(login_check, password_check, name_check, surname_check)) {
                                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        errorTV.setText("Database data insert error");
                                        errorTV.setVisibility(View.VISIBLE);
                                    }
                                }
                        }
                    }
                    }
                }
        );
    }
}
