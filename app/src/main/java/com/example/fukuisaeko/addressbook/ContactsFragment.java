package com.example.fukuisaeko.addressbook;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fukuisaeko.addressbook.addressbook.ContactsAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactsFragment extends Fragment {

    private ContactsAdapter adapter;

    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        FloatingActionButton add = (FloatingActionButton)view.findViewById(R.id.addButton);
        adapter = new ContactsAdapter();
      //  return inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}
