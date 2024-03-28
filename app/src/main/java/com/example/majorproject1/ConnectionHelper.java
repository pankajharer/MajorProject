package com.example.majorproject1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;

public class ConnectionHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME= "MajorDB";
    private static final int DATABASE_ID=1;
    private static final String TABLE_LOGIN = "login";

    private static final String TABLE_USER_INFO = "user_info";
    private static final String KEY_ID= "ID";
    private static final String KEY_USERNAME= "Username";
    private static final String KEY_PASS = "Password";
    private static final String KEY_PHONE = "Phone";


    public ConnectionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE_LOGIN + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_USERNAME + " TEXT UNIQUE," +  // Add UNIQUE constraint here
                    KEY_PASS + " TEXT," +
                    KEY_PHONE + " TEXT" +
                    ")");

            db.execSQL("CREATE TABLE user_info(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER," +
                    "name TEXT," +
                    "age TEXT," +
                    "weight TEXT," +
                    "gender TEXT" +
                    ")");


        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGIN);
           onCreate(db);
           db.execSQL("DROP TABLE IF EXISTS " + "medicine");
           onCreate(db);
    }

    public long addUser(String username,String Password,String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME, username);
        values.put(KEY_PASS, Password);
        values.put(KEY_PHONE, phone);

        long userId = db.insert(TABLE_LOGIN, null, values);
        db.close();

        return userId;

    }



    public ArrayList<LoginModel> fetchUser()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM "+TABLE_LOGIN, null);

        ArrayList<LoginModel> arrUser  = new ArrayList<>();
        while (cursor.moveToNext())
        {
            LoginModel model =new LoginModel();
            model.id =cursor.getInt(0);
            model.username =cursor.getString(1);
            model.password =cursor.getString(2);
            model.phoneno=cursor.getString(3);

            arrUser.add(model);
        }
        return arrUser;
    }

    public boolean addUserInfo(long userId, String name, String age, String weight, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("user_id", userId);
        values.put("name", name);
        values.put("age", age);
        values.put("weight", weight);
        values.put("gender", gender);

        db.insert("user_info", null, values);
        db.close();
        return true;
    }

    public long getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = -1;

        // Define the columns you want to retrieve
        String[] columns = {KEY_ID};

        // Define the WHERE clause
        String selection = KEY_USERNAME + " = ?";
        String[] selectionArgs = {username};

        // Query the login table to get the user ID for the given username
        Cursor cursor = db.query(TABLE_LOGIN, columns, selection, selectionArgs, null, null, null);

        // Check if the cursor has data
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(KEY_ID);
            if (columnIndex != -1) {
                userId = cursor.getLong(columnIndex);
            } else {

            }
        }

        // Close the cursor and the database
        cursor.close();
        db.close();

        return userId;
    }




}
