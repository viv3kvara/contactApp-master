package com.project.contactdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView contactList;
    DBHelper dbHelper;
    AdapterContact adapterContact;

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);

        actionBar = getSupportActionBar();

        contactList = findViewById(R.id.contactList);
        contactList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contactList.setLayoutManager(linearLayoutManager);

        dbHelper = new DBHelper(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditContact.class);
                startActivity(intent);
            }
        });

        loadDetails();
    }
    private void loadDetails() {

        adapterContact = new AdapterContact(this,dbHelper.getAllData());
        contactList.setAdapter(adapterContact);
    }
    protected void onResume() {
        super.onResume();
        loadDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAllContact) {
            dbHelper.deleteAllContact();
            onResume();
            Toast.makeText(this, "All Contact Has Been Deleted!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}