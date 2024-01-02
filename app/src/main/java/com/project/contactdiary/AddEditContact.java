package com.project.contactdiary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditContact extends AppCompatActivity {
    FloatingActionButton btnInsert;
    EditText inputFirstName, inputLastName, inputPhone, inputEmail;
    String id, first_name, last_name, phone, email, addedTime,updatedTime;

    Boolean isEditMode;
    DBHelper dbHelper = new DBHelper(this);
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);
        btnInsert = findViewById(R.id.btnInsert);
        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputPhone = findViewById(R.id.inputPhone);
        inputEmail = findViewById(R.id.inputEmail);
        actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setTitle("Add Contact");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);
//        int id1 = intent.getIntExtra("ID",555);
        if (isEditMode){
            actionBar.setTitle("Modify Contact");
            id = intent.getStringExtra("ID");
            email = intent.getStringExtra("EMAIL");
            first_name = intent.getStringExtra("FIRSTNAME");
            last_name = intent.getStringExtra("LASTNAME");
            phone = intent.getStringExtra("PHONE");
            addedTime = intent.getStringExtra("ADDEDTIME");
            updatedTime = intent.getStringExtra("UPDATEDTIME");
            inputEmail.setText(email);
            inputPhone.setText(phone);
            inputFirstName.setText(first_name);
            inputLastName.setText(last_name);
        }
//        else {
//            actionBar.setTitle("Add Contact");
//        }

//        if(id1!=555){
//            actionBar.setTitle("Update Contact");
//            id = intent.getStringExtra("id");
//            first_name = intent.getStringExtra("first_name");
//            last_name = intent.getStringExtra("last_name");
//            phone = intent.getStringExtra("contact");
//            email = intent.getStringExtra("email");
//            addedTime = intent.getStringExtra("addedTime");
//            updatedTime = intent.getStringExtra("updatedTime");
//
//            inputFirstName.setText("xyz");
//            inputLastName.setText(last_name);
//            inputPhone.setText(phone);
//            inputEmail.setText(email);
//        }
//        else {
//            actionBar.setTitle("Add Contact");
//        }
    }

    public void checkData(View view) {
        first_name = inputFirstName.getText().toString();
        last_name = inputLastName.getText().toString();
        phone = inputPhone.getText().toString();
        email = inputEmail.getText().toString();
        String timeStamp = ""+System.currentTimeMillis();

        if (!first_name.isEmpty() || !last_name.isEmpty() || !phone.isEmpty() || !email.isEmpty()){
            if (isEditMode){
                int count = dbHelper.updateContact(
                        ""+id,
                        ""+first_name,
                        ""+last_name,
                        ""+phone,
                        ""+email,
                        ""+addedTime,
                        ""+timeStamp
                );
                if (count > 0){
                    Toast.makeText(this, "Contact Has Been Updated As: "+first_name+" "+last_name+" "+count, Toast.LENGTH_LONG).show();
                    Intent intent3 = new Intent(this, MainActivity.class);
                    startActivity(intent3);
                }
                else {
                    Toast.makeText(this, "Contact Can't Be Updated "+count, Toast.LENGTH_LONG).show();
                }

            }
            else {
                int id = dbHelper.insertContact(
                        ""+first_name,
                        ""+last_name,
                        ""+phone,
                        ""+email,
                        ""+timeStamp,
                        ""+timeStamp
                );
                if (id > 0) {
                    Toast.makeText(this, "Contact Has Been Saved As: " + first_name + " " + last_name, Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);
                }
                else {
                    Toast.makeText(this, "Contact Can't Be Saved"+id, Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            Toast.makeText(this, "Nothing To Save...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}