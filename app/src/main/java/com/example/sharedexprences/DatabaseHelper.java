package com.example.sharedexprences;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private Context context;
    private static final String DATABASE_NAME = "shexp.db";

    //USERS TABLE
    private static String TABLE_NAME_USERS = "USERS";
    private static String COL_1_USERS = "ID";
    private static String COL_2_USERS = "LOGIN";
    private static String COL_3_USERS = "PASSWORD";
    private static String COL_4_USERS = "NAME";
    private static String COL_5_USERS = "SURNAME";
    private static String COL_6_USERS = "SUM";
    private static String COL_7_USERS = "HAS_PAYED";
    //GLOBAL EVENTS TABLE
    private static String TABLE_NAME_GE = "GE";
    private static String COL_1_GE = "ID";
    private static String COL_2_GE = "NAME";
    private static String COL_3_GE = "DESCRIBTION";
    private static String COL_4_GE = "AMMOUNT_OF_PARTICIPANTS";
    private static String COL_5_GE = "TOTAL_SUM";
    //GLOBAL EVENTS - USERS TABLE
    private static String TABLE_NAME_GEU = "GEU";
    private static String COL_1_GEU = "RECORD_ID";
    private static String COL_2_GEU = "GE_ID";
    private static String COL_3_GEU = "USER_ID";
    private static String COL_4_GEU = "IS_ADMIN";
    //GLOBAL EVENTS - EVENTS TABLE
    private static String TABLE_NAME_GEE = "GEE";
    private static String COL_1_GEE = "RECORD_ID";
    private static String COL_2_GEE = "EVENT_ID";
    private static String COL_3_GEE = "GE_ID";
    //EVENTS TABLE
    private static String TABLE_NAME_EVENTS = "EVENTS";
    private static String COL_1_EVENTS = "ID";
    private static String COL_2_EVENTS = "NAME";
    private static String COL_3_EVENTS = "DESCRIBTION";
    private static String COL_4_EVENTS = "TOTAL_SUM";
    private static String COL_5_EVENTS = "AMMOUNT_OF_PARTICIPANTS";
    private static String COL_6_EVENTS = "CHECK_FOTO";
    //EVENTS - USERS TABLE
    private static String TABLE_NAME_EU = "EU";
    private static String COL_1_EU = "RECORD_ID";
    private static String COL_2_EU = "E_ID";
    private static String COL_3_EU = "USER_ID";
    private static String COL_4_EU = "IS_ADMIN";
    //USER - USER PAYMENT TABLE
    private static String TABLE_NAME_UU = "UU";
    private static String COL_1_UU = "RECORD_ID";
    private static String COL_2_UU = "DEBTOR_ID";
    private static String COL_3_UU = "PAYER_ID";
    private static String COL_4_UU = "EVENT_ID";
    private static String COL_5_UU = "SUM";
    private static String COL_6_UU = "HAS_PAYED";
    //LOCAL USER
    private static String TABLE_NAME_LU = "LU";
    private static String COL_1_LU = "USER_ID";
    private static String COL_2_LU = "NAME";
    private static String COL_3_LU = "SURNAME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String query_USERS = "CREATE TABLE "+TABLE_NAME_USERS+
                " ("+COL_1_USERS+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_USERS + " TEXT, "
                + COL_3_USERS + " TEXT, "
                + COL_4_USERS + " TEXT, "
                + COL_5_USERS + " TEXT, "
                + COL_6_USERS + " REAL, "
                + COL_7_USERS + " INTEGER);";
        String query_GE  = "CREATE TABLE "+TABLE_NAME_GE+
                " ("+COL_1_GE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_GE + " TEXT, "
                + COL_3_GE + " TEXT, "
                + COL_4_GE + " INTEGER, "
                + COL_5_GE + " REAL);";
        String query_GEU  = "CREATE TABLE "+TABLE_NAME_GEU+
                " ("+COL_1_GEU+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_GEU + " INTEGER, "
                + COL_3_GEU + " INTEGER, "
                + COL_4_GEU + " INTEGER);";
        String query_GEE  = "CREATE TABLE "+TABLE_NAME_GEE+
                " ("+COL_1_GEE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_GEE + " INTEGER, "
                + COL_3_GEE + " INTEGER);";
        String query_EU  = "CREATE TABLE "+TABLE_NAME_EU+
                " ("+COL_1_EU+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_EU + " INTEGER, "
                + COL_3_EU + " INTEGER, "
                + COL_4_EU + " INTEGER);";
        String query_EVENTS  = "CREATE TABLE "+TABLE_NAME_EVENTS+
                " ("+COL_1_EVENTS+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_EVENTS + " INTEGER, "
                + COL_3_EVENTS + " TEXT, "
                + COL_4_EVENTS + " REAL, "
                + COL_5_EVENTS + " INTEGER, "
                + COL_6_EVENTS + " TEXT);";
        String query_UU  = "CREATE TABLE "+TABLE_NAME_UU+
                " ("+COL_1_UU+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_UU + " INTEGER, "
                + COL_3_UU + " INTEGER, "
                + COL_4_UU + " INTEGER, "
                + COL_5_UU + " REAL, "
                + COL_6_UU + " INTEGER);";
        String query_LU  = "CREATE TABLE "+TABLE_NAME_LU+
                " ("+COL_1_LU+" INTEGER, "
                + COL_2_LU + " TEXT, "
                + COL_3_LU + " TEXT);";
        db.execSQL(query_USERS);
        Log.d(TAG, "USERS-OK");
        db.execSQL(query_GE);
        Log.d(TAG, "GE-OK");
        db.execSQL(query_GEE);
        Log.d(TAG, "GEE-OK");
        db.execSQL(query_GEU);
        Log.d(TAG, "GEU-OK");
        db.execSQL(query_EU);
        Log.d(TAG, "EU-OK");
        db.execSQL(query_EVENTS);
        Log.d(TAG, "EVENTS-OK");
        db.execSQL(query_UU);
        Log.d(TAG, "UU-OK");
        db.execSQL(query_LU);
        Log.d(TAG, "LU-OK");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GEU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_UU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LU);
        onCreate(db);
    }

    boolean addUser(String e_login, String e_password, String e_name, String e_surname){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_USERS, e_login);
        cv.put(COL_3_USERS, e_password);
        cv.put(COL_4_USERS, e_name);
        cv.put(COL_5_USERS, e_surname);
        cv.put(COL_6_USERS, 0);
        cv.put(COL_7_USERS, 1);
        long result = db.insert(TABLE_NAME_USERS, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Admin funcs
    void clearAllTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME_USERS);
        db.execSQL("delete from "+TABLE_NAME_GE);
        db.execSQL("delete from "+TABLE_NAME_GEU);
        db.execSQL("delete from "+TABLE_NAME_GEE);
        db.execSQL("delete from "+TABLE_NAME_EU);
        db.execSQL("delete from "+TABLE_NAME_EVENTS);
        db.execSQL("delete from "+TABLE_NAME_UU);
        db.execSQL("delete from "+TABLE_NAME_LU);
        Log.d(TAG, "Table clearing complete");
    }
    //Register functions
    boolean is_loginAvaiable(String e_login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME_USERS+" where login == \""+e_login+"\"", null);
        if (cursor.getCount()==0){
            return true;
        }else{
            return false;
        }
    }
    //Login(MainActivity) functions
    boolean is_logged(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_LU, null);
        if(cursor.getCount()==0){
            return false;
        }else{
            return true;
        }
    }
    boolean loginAttemptResponce(String e_login, String e_password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME_USERS+" where login == \""+e_login+"\" and password == \""+e_password+"\"", null);
        if (cursor.getCount()==1){
            cursor.moveToNext();
            ContentValues cv = new ContentValues();
            cv.put(COL_1_LU, Integer.parseInt(cursor.getString(0)));
            cv.put(COL_2_LU, cursor.getString(3));
            cv.put(COL_3_LU, cursor.getString(4));
            long result = db.insert(TABLE_NAME_LU, null, cv);
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
    //UserPage functions
    Cursor returnCursorWithLUData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_LU, null);
        return cursor;
    }
    void luTableCleaning(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME_LU);
    }
    boolean is_inGE(Integer userID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_GEU +
                " where "+ COL_3_GEU + " == " + userID, null);
        if(cursor.getCount() == 0){
            return false;
        }else {
            return true;
        }
    }
    //get DB functions
        //USERS
        Cursor returnUsersDB(){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_USERS, null);
        }
        Cursor returnCertainUser(Integer userID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_USERS+" where "+COL_1_USERS+" == "+userID, null);
        }
        Cursor returnUsersForCertainGE(Integer geID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_GEU+" where "+COL_2_GEU+" == "+geID, null);
        }
        Cursor returnUsersForCertainEvent(Integer eventID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_EU+" where "+COL_2_EU+" == "+eventID, null);
        }
        //GE
        Cursor returnGEdb(){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_GE, null);
        }
        Cursor returnCertainGE(Integer geID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_GE+" where "+COL_1_GE+" == "+geID, null);
        }
        Cursor returnGEdbForCertainUser(Integer userID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_GEU+" where "+COL_3_GEU+" == "+userID,
                    null);
        }
        boolean deleteGE(Integer geID) {
            boolean is_deleted = true;
            Integer ammountOfDeletedRows;
            SQLiteDatabase db = getReadableDatabase();
            Cursor events = returnEventsForCertainGE(geID);
            if(events.getCount()!=0){
                Integer eventID;
                while(events.moveToNext()){
                    eventID=Integer.parseInt(events.getString(1));
                    deleteEvent(eventID);
                }
            }
            ammountOfDeletedRows=db.delete(TABLE_NAME_GEU, COL_2_GEU+" = ?", new String[]{geID.toString()});
            if(ammountOfDeletedRows==0){
                is_deleted=false;
                Log.d("deleteGE", "GEU-false");
            }else{
                Log.d("deleteGE", "GEU-OK");
            }
            ammountOfDeletedRows=db.delete(TABLE_NAME_GE, COL_1_GE+" = ?", new String[]{geID.toString()});
            if(ammountOfDeletedRows==0){
                is_deleted=false;
                Log.d("deleteGE", "GE-false");
            }else{
                Log.d("deleteGE", "GE-OK");
            }
            return is_deleted;
        }
        //EVENTS
        Cursor returnEventsDB(){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_EVENTS,
                    null);
        }
        Cursor returnCertainEvent(Integer eventID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_EVENTS+" where "+COL_1_EVENTS+" == "+eventID, null);
        }
        Cursor returnEventsForCertainGE(Integer geID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_GEE+" where "+COL_3_GEE+" == "+geID,
                    null);
        }
        boolean deleteEvent(Integer eventID) {
            boolean is_deleted = true;
            Integer ammountOfDeletedRows;
            SQLiteDatabase db = getReadableDatabase();
            ammountOfDeletedRows=db.delete(TABLE_NAME_EU, COL_2_EU+" = ?", new String[]{eventID.toString()});
            if(ammountOfDeletedRows==0){
                is_deleted=false;
                Log.d("deleteEvent", "EU-false");
            }else{
                Log.d("deleteEvent", "EU-OK");
            }
            ammountOfDeletedRows=db.delete(TABLE_NAME_GEE, COL_2_GEE+" = ?", new String[]{eventID.toString()});
            if(ammountOfDeletedRows==0){
                is_deleted=false;
                Log.d("deleteEvent", "GEE-false");
            }else{
                Log.d("deleteEvent", "GEE-OK");
            }
            ammountOfDeletedRows=db.delete(TABLE_NAME_EVENTS, COL_1_EVENTS+" = ?", new String[]{eventID.toString()});
            if(ammountOfDeletedRows==0){
                is_deleted=false;
                Log.d("deleteEvent", "USERS-false");
            }else{
                Log.d("deleteEvent", "USERS-OK");
            }
            if(clearUUforCertainEvent(eventID)==false){
                is_deleted=false;
            }
            return is_deleted;
        }
        //UU
        boolean clearUUforCertainEvent(Integer eventID){
            boolean is_deleted;
            SQLiteDatabase db = getReadableDatabase();
            Integer ammountOfDeletedRows=db.delete(TABLE_NAME_UU, COL_4_UU+" = ?",
                    new String[]{eventID.toString()});
            if(ammountOfDeletedRows==0){
                is_deleted=false;
                Log.d("clearUUforCertainEvent", "false");
            }else{
                Log.d("clearUUforCertainEvent", "OK");
                is_deleted=true;
            }
            return  is_deleted;
        }
        Cursor returnAllDebtsForCertainUser(Integer userID){
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select * from "+TABLE_NAME_UU+" where "+
                    COL_3_UU+" == "+userID.toString(),null);
        }
    //New GE creation functions
    boolean addNewGE(String e_name, String e_description,
                     Integer e_ammountOfParticipants, Integer e_sum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_2_GE, e_name);
        cv.put(COL_3_GE, e_description);
        cv.put(COL_4_GE, e_ammountOfParticipants);
        cv.put(COL_5_GE, e_sum);
        long result = db.insert(TABLE_NAME_GE, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    boolean addNewRecordToGEUdb(Integer e_geID, Integer e_userID, boolean is_admin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_GEU, e_geID);
        cv.put(COL_3_GEU, e_userID);
        if(is_admin==false){
            cv.put(COL_4_GEU, 0);
        }else{
            cv.put(COL_4_GEU, 1);
        }
        long result = db.insert(TABLE_NAME_GEU, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    //GEpage functions
    boolean is_geEmpty(Integer geID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME_GEE+" where " + COL_3_GEE + " == " + geID, null);
        if (cursor.getCount()==0){
            return true;
        }else{
            return false;
        }
    }
    boolean is_GEadmin(Integer geID, Integer userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME_GEU+" where " + COL_2_GEU + " == " + geID+
                " and "+COL_3_GEU+" == "+userID, null);
        cursor.moveToNext();
        if(Integer.parseInt(cursor.getString(3))==1){
            return  true;
        }else{
            return false;
        }
    }
    //New Event Creation functions
    boolean addNewEvent(String e_name, String e_description,
                     Integer e_ammountOfParticipants, Integer e_sum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_2_EVENTS, e_name);
        cv.put(COL_3_EVENTS, e_description);
        cv.put(COL_4_EVENTS, e_sum);
        cv.put(COL_5_EVENTS, e_ammountOfParticipants);
        cv.put(COL_6_EVENTS, "null");
        long result = db.insert(TABLE_NAME_EVENTS, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    boolean addNewRecordToGEEdb(Integer e_geID, Integer e_eventID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_GEE, e_eventID);
        cv.put(COL_3_GEE, e_geID);
        long result = db.insert(TABLE_NAME_GEE, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    boolean addNewRecordToEUdb(Integer e_eventID, Integer e_userID, boolean is_admin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_EU, e_eventID);
        cv.put(COL_3_EU, e_userID);
        if(is_admin==false){
            cv.put(COL_4_EU, 0);
        }else{
            cv.put(COL_4_EU, 1);
        }
        long result = db.insert(TABLE_NAME_EU, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    boolean addNewRecordToUUdb(Integer e_eventID, Integer e_adminID, Integer e_userID, float e_debt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_UU, e_adminID);
        cv.put(COL_3_UU, e_userID);
        cv.put(COL_4_UU, e_eventID);
        cv.put(COL_5_UU, e_debt);
        cv.put(COL_6_UU, 0);
        long result = db.insert(TABLE_NAME_UU, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    //EVENT INFO functions
    boolean is_EventAdmin(Integer e_eventID, Integer e_userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME_EU+" where " + COL_2_EU + " == " + e_eventID+
                " and "+COL_3_GEU+" == "+e_userID, null);
        cursor.moveToNext();
        if(Integer.parseInt(cursor.getString(3))==1){
            return  true;
        }else{
            return false;
        }
    }
    boolean updateEvent(Integer eventID, String e_name, String e_sum, String e_describtion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_EVENTS, e_name);
        cv.put(COL_3_EVENTS, e_describtion);
        cv.put(COL_5_EVENTS, e_sum);
        Integer result = db.update(TABLE_NAME_EVENTS, cv, COL_1_EVENTS+" = ?",
                new String[]{eventID.toString()});
        if(result==0){
            return false;
        }else{
            return true;
        }
    }
}
