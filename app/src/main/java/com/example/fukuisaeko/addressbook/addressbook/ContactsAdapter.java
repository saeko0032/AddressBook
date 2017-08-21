package com.example.fukuisaeko.addressbook.addressbook;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fukuisaeko.addressbook.R;
import com.example.fukuisaeko.addressbook.addressbook.data.DatabaseDescription;

/**
 * Created by fukuisaeko on 2017-08-16.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    // inner class
    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView contactTextView;
        long id;
        Cursor cursor;

        ContactsViewHolder(View itemViews) {
            super(itemViews);
            //set textid
        }

        public void setRowID(long rowID) {
        // set db id
        }
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // create  a simple list item
        // inflate the android.R.layout.simple_list_item_1 layout
        View view = inflater.inflate(R.layout.simple_list_item_1, parent, false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;  // return current item's ViewHolder

    }

    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.tvContact.setText(cursor.getString(cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_NAME)));
        holder.setRowID(cursor.getLong(cursor.getColumnIndex(DatabaseDescription.Contact._ID)));
    }


    @Override
    public int getItemCount() {
        Cursor cursor;
        int rows = cursor.getCount():

        return rows;
    }

}
