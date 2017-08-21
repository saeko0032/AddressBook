package com.example.fukuisaeko.addressbook.addressbook.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.fukuisaeko.addressbook.R;

public class AddressBookContentProvider extends ContentProvider {

    private AddressBookDatabaseHelper dbHelper;

    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CONTACTS = 2;
    private static final int ONE_CONTACT = 1;

    static {
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, DatabaseDescription.Contact.TABLE_NAME, CONTACTS);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, DatabaseDescription.Contact.TABLE_NAME + " /#", ONE_CONTACT);
    }

    public AddressBookContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteRowId;

        switch (uriMatcher.match(uri)) {
            case ONE_CONTACT:
                // content(1st seg)->com.example...(2nd seg)->id(3rd and last seg)
                String id = uri.getLastPathSegment();
                deleteRowId = dbHelper.getWritableDatabase().delete(DatabaseDescription.Contact.TABLE_NAME,
                        DatabaseDescription.Contact._ID + " = " + id, selectionArgs);
                break;
            default:
                throw new SQLException(getContext().getString(R.string.invalid_delete_uri));
        }

        if (deleteRowId != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleteRowId;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newContact = null;

        switch (uriMatcher.match(uri)) {
            case CONTACTS :
                // insert : add new data the end of data
                // return id value
                // return value of -1
                long rowid = dbHelper.getWritableDatabase().insert(
                        DatabaseDescription.Contact.TABLE_NAME,
                        null,
                        values
                );
                if (rowid != -1) {
                    newContact = DatabaseDescription.Contact.buildContactUri(rowid);
                    getContext().getContentResolver().notifyChange(uri, null);
                } else {
                    throw new SQLException(getContext().getString(R.string.insert_failed));
                }
                break;
            default:
                throw new SQLException(getContext().getString(R.string.insert_failed));
        }
        return newContact;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new AddressBookDatabaseHelper(getContext());
        return true;
    }

    /**
     * @See ContentProvider#query
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseDescription.Contact.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ONE_CONTACT:
                // one row
                queryBuilder.appendWhere(DatabaseDescription.Contact._ID +
                        " = " + uri.getLastPathSegment());
                ;
                break;
            case CONTACTS:
                // all rows
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_query_uri));
        }

        // not execute yet
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int numberOfRowsUpdated;
        switch (uriMatcher.match(uri)) {
            case ONE_CONTACT:
                String id = uri.getLastPathSegment();
                numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                        DatabaseDescription.Contact.TABLE_NAME,
                        values,
                        DatabaseDescription.Contact._ID + " = " + id,
                        selectionArgs);
                break;
            default:
                throw new SQLException(getContext().getString(R.string.invalid_update_uri));

        }
        if (numberOfRowsUpdated != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numberOfRowsUpdated;
    }
}
