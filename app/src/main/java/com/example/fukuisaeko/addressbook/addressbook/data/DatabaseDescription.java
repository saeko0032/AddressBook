package com.example.fukuisaeko.addressbook.addressbook.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class provides content to intercat with contentProvider
 * @author saeko
 */
public class DatabaseDescription {
    // Content provider's name = package name
    public static final String AUTHORITY = "com.example.fukuisaeko.addressbook.addressbook.data";
    // for contentprovider like http://
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * @see BaseColumns
     */
    public static final class Contact implements BaseColumns {
        public static final String TABLE_NAME = "contacts";

        // uri for the contacts table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's name
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_STREET = "sreet";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_ZIP = "zip";

        // unique record id
        public static final Uri buildContactUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
