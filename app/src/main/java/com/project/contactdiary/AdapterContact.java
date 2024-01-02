package com.project.contactdiary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder>{
    private final Context context;
    private final ArrayList<GetSetContact> contactList;

    public AdapterContact(Context context, ArrayList<GetSetContact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_record,parent,false);
        ContactViewHolder vh = new ContactViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        GetSetContact getSetContact = contactList.get(position);
        String id = getSetContact.getId();
        String first_name = getSetContact.getFirst_name();
        String last_name = getSetContact.getLast_name();
        String ss=first_name+" "+last_name;
        holder.contact_name.setText(ss);


        holder.contact_number_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactDetails.class);
                intent.putExtra("contactId", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView contact_name;
        ImageView contact_number_dial, contact_image;
        CardView cardView;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number_dial = itemView.findViewById(R.id.contact_number_dial);
            contact_image = itemView.findViewById(R.id.contact_image);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
