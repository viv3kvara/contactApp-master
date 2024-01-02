package com.project.contactdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class ContactDetails extends AppCompatActivity {
    String id, firstname, lastname, contact, mail, addTime, updateTime;
    DBHelper dbHelper;
    ActionBar actionBar;
    TextView first_last_name, phone, email, addedTime, updatedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Contact Details");

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        first_last_name = findViewById(R.id.first_last_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        addedTime = findViewById(R.id.addedTime);
        updatedTime = findViewById(R.id.updatedTime);

        loadContactDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_top_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.updateContact:
                Intent intent = new Intent(getApplicationContext(),AddEditContact.class);
//                intent.putExtra("ID",Integer.valueOf(id));
                intent.putExtra("ID",id);
                intent.putExtra("FIRSTNAME",firstname);
                intent.putExtra("LASTNAME",lastname);
                intent.putExtra("EMAIL",mail);
                intent.putExtra("PHONE",contact);
                intent.putExtra("ADDEDTIME",addTime);
                intent.putExtra("UPDATEDTIME",updateTime);
                intent.putExtra("isEditMode",true);
                startActivity(intent);
                break;

            case R.id.deleteContact:
                dbHelper.deleteContact(id);
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "Contact Has Been Deleted!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void loadContactDetails() {
        String selectQuery =  "SELECT * FROM "+Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id + "\"";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                //get data
                firstname =  ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_FIRSTNAME));
                lastname =  ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LASTNAME));
                contact = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                mail = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                addTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                updateTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME));

                //convert time to dd/mm/yy hh:mm:aa format
                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = ""+ DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = ""+ DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                //set data
                String ss=firstname+" "+lastname;
                first_last_name.setText(ss);

                phone.setText(contact);
                email.setText(mail);
                addedTime.setText(timeAdd);
                updatedTime.setText(timeUpdate);
            }while (cursor.moveToNext());
        }
        db.close();
    }
}