package com.example.fukuisaeko.addressbook.addressbook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fukuisaeko on 2017-08-16.
 */

public class AddressBookDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "address_book";
    public static final int DATABASE_VERSION = 1;


    public AddressBookDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Contact.TABLE_NAME + " ( " +
                        DatabaseDescription.Contact._ID + " Integer primary key, " +
                        DatabaseDescription.Contact.COLUMN_NAME + " TEXT " +
                        DatabaseDescription.Contact.COLUMN_PHONE + " TEXT, " +
                        DatabaseDescription.Contact.COLUMN_EMAIL + " TEXT, " +
                        DatabaseDescription.Contact.COLUMN_CITY+ " TEXT, " +
                        DatabaseDescription.Contact.COLUMN_STATE + " TEXT, " +
                        DatabaseDescription.Contact.COLUMN_ZIP + " TEXT" + " );";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + DatabaseDescription.Contact.TABLE_NAME);
        this.onCreate(sqLiteDatabase);

    }
}
