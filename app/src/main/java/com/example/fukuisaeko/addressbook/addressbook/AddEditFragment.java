package com.example.fukuisaeko.addressbook.addressbook;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fukuisaeko.addressbook.R;
import com.example.fukuisaeko.addressbook.addressbook.data.DatabaseDescription;

import static android.R.attr.data;

/**
 * Created by fukuisaeko on 2017-08-16.
 */

public class AddEditFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // loader mush have unique id in my application
    private final static int CONTACT_LOADER = 0;
    private Uri contactUri; // from main activity
    private TextInputLayout nameTextLayout;
    private TextInputLayout phoneTextLayout;
    private TextInputLayout emailTextLayout;
    private TextInputLayout streetTextLayout;
    private TextInputLayout cityTextLayout;
    private TextInputLayout stateTextLayout;
    private TextInputLayout zipTextLayout;
    private FloatingActionButton saveContactFabBtn;

    // create a View for Fragment


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_add_edit,
                container,
                false
        );

        nameTextLayout = (TextInputLayout)view.findViewById(R.id.nameTextInputLayout);
        phoneTextLayout = (TextInputLayout)view.findViewById(R.id.phoneTextInputLayout);
        emailTextLayout = (TextInputLayout)view.findViewById(R.id.emailTextInputLayout);
        streetTextLayout = (TextInputLayout)view.findViewById(R.id.streetTextInputLayout);
        cityTextLayout = (TextInputLayout)view.findViewById(R.id.cityTextInputLayout);
        stateTextLayout = (TextInputLayout)view.findViewById(R.id.stateTextInputLayout);
        zipTextLayout = (TextInputLayout)view.findViewById(R.id.zipTextInputLayout);

        saveContactFabBtn = (FloatingActionButton)view.findViewById(R.id.fab);


        saveContactFabBtn.setOnClickListener(saveDataListner);
        return view;

    }

    private View.OnClickListener saveDataListner =
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    saveContact(); //this is method to save the data
                }
            };

    private void saveContact() {
        ContentValues contentValues =
                new ContentValues();
        contentValues.put(DatabaseDescription.Contact.COLUMN_NAME,
                nameTextLayout.getEditText().getText().toString() );
        contentValues.put(DatabaseDescription.Contact.COLUMN_PHONE,
                phoneTextLayout.getEditText().getText().toString());
        contentValues.put(DatabaseDescription.Contact.COLUMN_EMAIL,
                emailTextLayout.getEditText().getText().toString() );
        contentValues.put(DatabaseDescription.Contact.COLUMN_STREET,
                streetTextLayout.getEditText().getText().toString());
        contentValues.put(DatabaseDescription.Contact.COLUMN_CITY,
                cityTextLayout.getEditText().getText().toString() );
        contentValues.put(DatabaseDescription.Contact.COLUMN_STATE,
                stateTextLayout.getEditText().getText().toString());
        contentValues.put(DatabaseDescription.Contact.COLUMN_ZIP,
                zipTextLayout.getEditText().getText().toString());

        // you need a URI
        // this Uri is used yo call contentResolver
        // insert the data into addressBook Content
        Uri newContactUri = getActivity().
                getContentResolver()
                .insert(DatabaseDescription.Contact.CONTENT_URI
                        ,contentValues);
        Toast.makeText(getActivity(),"Data inserted Succesfully",
                Toast.LENGTH_SHORT).show();
        //Change the Toast to SnackBar
        //SnackBar = notification feedback to the user
        // and you add actions to snackBar like undo, cancel, ok

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                contactUri,
                null, // all columns
                null, //return all rows
                null, // no clause
                null // no sort
                );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // loading finishe
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_NAME);
            int phoneIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_PHONE);
            int emailIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_EMAIL);
            int streetIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_STREET);
            int cityIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_CITY);
            int stateIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_STATE);
            int zipIndex = cursor.getColumnIndex(DatabaseDescription.Contact.COLUMN_ZIP);

            nameTextLayout.getEditText().setText(
                 cursor.getString(nameIndex)
            );

            phoneTextLayout.getEditText().setText(
                    cursor.getString(phoneIndex)
            );

            emailTextLayout.getEditText().setText(
                    cursor.getString(emailIndex)
            );

            streetTextLayout.getEditText().setText(
                    cursor.getString(streetIndex)
            );

            cityTextLayout.getEditText().setText(
                    cursor.getString(cityIndex)
            );

            stateTextLayout.getEditText().setText(
                    cursor.getString(stateIndex)
            );

            zipTextLayout.getEditText().setText(
                    cursor.getString(zipIndex)
            );

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
