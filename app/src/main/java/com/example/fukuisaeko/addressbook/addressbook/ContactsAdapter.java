package com.example.fukuisaeko.addressbook.addressbook;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

    // interface implemented by ContactsFragment to respond
    // when the user touches an item in the RecyclerView
    public interface ContactClickListener {
        void onClick(Uri contactUri);
    }

    // nested subclass of RecyclerView.ViewHolder used to implement
    // the view-holder pattern in the context of a RecyclerView
    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        private long rowID;

        // configures a RecyclerView item's ViewHolder
        public ContactsViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);

            // attach listener to itemView
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        // executes when the contact in this ViewHolder is clicked
                        @Override
                        public void onClick(View view) {
                            clickListener.onClick(DatabaseDescription.Contact.buildContactUri(rowID));
                        }
                    }
            );
        }

        // set the database row ID for the contact in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }
    }

    // ContactsAdapter instance variables
    private Cursor cursor = null;
    private final ContactClickListener clickListener;

    // constructor
    public ContactsAdapter(ContactClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // sets up new list item and its ViewHolder
    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);
        return new ContactsViewHolder(view); // return current item's ViewHolder
    }

    // sets the text of the list item to display the search tag
    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.setRowID(cursor.getLong(cursor.getColumnIndex(DatabaseDescription.Contact._ID)));
        holder.textView.setText(cursor.getString(cursor.getColumnIndex(
                DatabaseDescription.Contact.COLUMN_NAME)));
    }

    // returns the number of items that adapter binds
    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    // swap this adapter's current Cursor for a new one
    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}