package com.example.fukuisaeko.addressbook.addressbook.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.fukuisaeko.addressbook.R;
import com.example.fukuisaeko.addressbook.addressbook.data.DatabaseDescription.Contact;

public class AddressBookContentProvider extends ContentProvider {

    // used to access the database
    private AddressBookDatabaseHelper dbHelper;

    // related with integer contacts, one_contact
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CONTACTS = 2;
    private static final int ONE_CONTACT = 1;

    static {
        // ex:)content://com.example.fukuisaeko.addressbook.addressbook.data/contacts
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, Contact.TABLE_NAME, CONTACTS);

        // ex:)content://com.example.fukuisaeko.addressbook.addressbook.data/contacts/#
        // uri for contact with the specified id (#) primary key
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, Contact.TABLE_NAME + "/#", ONE_CONTACT);
    }

    public AddressBookContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // create databaseHelper
        dbHelper = new AddressBookDatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null; // use to specify MINE TYPE
    }

    // content provider->synchronized by myself SQLite = synchronized DB
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // if it's multiple tables, we will change
        queryBuilder.setTables(Contact.TABLE_NAME);

        // what url we will parse
        switch (uriMatcher.match(uri)) {
            case ONE_CONTACT:
                // one row
                queryBuilder.appendWhere(DatabaseDescription.Contact._ID +
                        "=" + uri.getLastPathSegment());
                break;
            case CONTACTS:
                // all contacts will be selected
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_query_uri));
        }

        // not execute yet
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder); // check db grouping
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    // insert a new contact in the db
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newContactUri = null;

        switch (uriMatcher.match(uri)) {
            case CONTACTS :
                // insert : add new data the end of data
                // return id value
                // return value of -1
                long rowId = dbHelper.getWritableDatabase().insert(
                        Contact.TABLE_NAME, null, values);
                // sqlite row ids start at 1
                if (rowId > 0) {
                    // new unique is for contacts
                    newContactUri = Contact.buildContactUri(rowId);
                    getContext().getContentResolver().notifyChange(uri, null);
                } else {
                    throw new SQLException(getContext().getString(R.string.insert_failed));
                }
                break;
            default:
                throw new SQLException(getContext().getString(R.string.insert_failed));
        }
        return newContactUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int numberOfRowsUpdated; // 1:true 0:false
        switch (uriMatcher.match(uri)) {
            case ONE_CONTACT:
                String id = uri.getLastPathSegment();
                numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                        Contact.TABLE_NAME,
                        values,
                        Contact._ID + "=" + id,
                        selectionArgs);
                break;
            default:
                throw new SQLException(getContext().getString(R.string.invalid_update_uri));

        }
        if (numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numberOfRowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numberOfRowsDeleted; // 0:failed 1:success(this time)

        switch (uriMatcher.match(uri)) {
            case ONE_CONTACT:
                // content(1st seg)->com.example...(2nd seg)->id(3rd and last seg)
                String id = uri.getLastPathSegment();
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(Contact.TABLE_NAME,
                        Contact._ID + "=" + id, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_delete_uri) + uri);
        }

        if (numberOfRowsDeleted != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numberOfRowsDeleted;
    }
}
