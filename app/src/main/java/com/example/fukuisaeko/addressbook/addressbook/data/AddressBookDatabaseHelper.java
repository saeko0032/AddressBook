package com.example.fukuisaeko.addressbook.addressbook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fukuisaeko.addressbook.addressbook.data.DatabaseDescription.Contact;

/**
 * Created by fukuisaeko on 2017-08-16.
 */

public class AddressBookDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AddressBook.db";
    public static final int DATABASE_VERSION = 1;

    // constructor
    public AddressBookDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + Contact.TABLE_NAME + " ( " +
                        Contact._ID + " integer primary key, " +
                        Contact.COLUMN_NAME + " TEXT " +
                        Contact.COLUMN_PHONE + " TEXT, " +
                        Contact.COLUMN_EMAIL + " TEXT, " +
                        Contact.COLUMN_CITY+ " TEXT, " +
                        Contact.COLUMN_STATE + " TEXT, " +
                        Contact.COLUMN_ZIP + " TEXT" + " );";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + DatabaseDescription.Contact.TABLE_NAME);
        this.onCreate(sqLiteDatabase);

    }
}
