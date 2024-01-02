package com.project.contactdiary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    public int insertContact(String firstName, String lastName, String phone, String email, String addedTime, String updatedTime){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.C_FIRSTNAME,firstName);
        contentValues.put(Constants.C_LASTNAME,lastName);
        contentValues.put(Constants.C_PHONE,phone);
        contentValues.put(Constants.C_EMAIL,email);
        contentValues.put(Constants.C_ADDED_TIME,addedTime);
        contentValues.put(Constants.C_UPDATED_TIME,updatedTime);

        long id = db.insert(Constants.TABLE_NAME,null,contentValues);

        db.close();

        return (int) id;
    }

    public int updateContact(String id, String firstName, String lastName, String phone, String email, String addedTime, String updatedTime){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.C_FIRSTNAME,firstName);
        contentValues.put(Constants.C_LASTNAME,lastName);
        contentValues.put(Constants.C_PHONE,phone);
        contentValues.put(Constants.C_EMAIL,email);
        contentValues.put(Constants.C_ADDED_TIME,addedTime);
        contentValues.put(Constants.C_UPDATED_TIME,updatedTime);

        int count = db.update(Constants.TABLE_NAME, contentValues, Constants.C_ID + " =?", new String[]{id});

        db.close();

        return count;
    }

    void deleteContact(String id){
        SQLiteDatabase db =  getWritableDatabase();
        db.delete(Constants.TABLE_NAME, "id=?", new String[]{id});
        db.close();
    }

    void deleteAllContact(){
        SQLiteDatabase db =  getWritableDatabase();
        db.execSQL("DELETE FROM "+Constants.TABLE_NAME);
        db.close();
    }

    public ArrayList<GetSetContact> getAllData(){
        ArrayList<GetSetContact> arrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+Constants.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                GetSetContact modelContact = new GetSetContact(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_FIRSTNAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LASTNAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME))
                );
                arrayList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}
