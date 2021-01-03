package com.example.sharedexprences;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FinancePage extends AppCompatActivity {

    private static final String TAG = "FinancePage";

    ListView debtorsLV, youOweLV;
    TextView yourDebt, peopleOweYou;
    ImageView emoji;
    Integer userID;

    DatabaseHelper myDb;

    ArrayList<FinancePagePerson> debtors, peopleYouOweTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.finance_page_layout);

        myDb = new DatabaseHelper(FinancePage.this);

        debtorsLV = (ListView)findViewById(R.id.debtorsLvID);
        youOweLV = (ListView)findViewById(R.id.youOweToLvID);
        yourDebt = (TextView)findViewById(R.id.debtID);
        peopleOweYou = (TextView)findViewById(R.id.peopleOweYouID);
        emoji = (ImageView)findViewById(R.id.emojiID);
        if(getIntent().getStringExtra("LOCAL_USER_ID").equals(null)){
            Log.d(TAG, "Empty intent extra");
        }
        userID = Integer.parseInt(getIntent().getStringExtra("LOCAL_USER_ID"));

        debtors = new ArrayList<>();
        peopleYouOweTo = new ArrayList<>();

        setFace();

    }

    void setFace(){
        Integer sumYouOwe, potentialIncome;
        sumYouOwe=0;
        potentialIncome=0;
        Cursor cursor;
        cursor = myDb.returnDebtors(userID);
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                if(!cursor.getString(4).equals(null)) {
                    potentialIncome += Integer.parseInt(cursor.getString(4));
                }
            }
        }
        cursor = myDb.returnPeopleYouOwe(userID);
        cursor = myDb.returnDebtors(userID);
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                if(!cursor.getString(4).equals(null)) {
                    sumYouOwe += Integer.parseInt(cursor.getString(4));
                }
            }
        }

        Toast.makeText(getApplicationContext(), Integer.toString(cursor.getCount()), Toast.LENGTH_LONG).show();
        if(sumYouOwe>potentialIncome){
            emoji.setImageResource(R.drawable.ic_sad_face);
        }else{
            emoji.setImageResource(R.drawable.ic_cool_face);
        }
    }
}
