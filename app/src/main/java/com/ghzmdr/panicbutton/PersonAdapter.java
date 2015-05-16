package com.ghzmdr.panicbutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ghzmdr on 19/01/15.
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, ArrayList<Person> contactsToShow) {
        super(context, 0, contactsToShow);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Person p = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);

        ImageButton btnRemove = (ImageButton) convertView.findViewById(R.id.contact_item_remove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonManager.removePerson(p, getContext());
                PersonAdapter.this.remove(p);
                PersonAdapter.this.notifyDataSetChanged();
            }
        });
        TextView name = (TextView) convertView.findViewById(R.id.contact_item_name),
                number = (TextView) convertView.findViewById(R.id.contact_item_number);

        name.setText(p.getName());
        number.setText(p.getNumber());
        return convertView;
    }
}
