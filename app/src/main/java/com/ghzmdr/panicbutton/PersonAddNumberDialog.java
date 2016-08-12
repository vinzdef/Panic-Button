package com.ghzmdr.panicbutton;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by ghzmdr on 19/01/15.
 */
public class PersonAddNumberDialog {
    private AlertDialog dialog;

    public PersonAddNumberDialog(final Context context, final ListView toUpdate) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);

        final EditText textName = new EditText(context);
        textName.setHint("Name");

        final EditText textNumber = new EditText(context);
        textNumber.setHint("Number");
        textNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

        root.addView(textName);
        root.addView(textNumber);

        dialog = builder
                    .setView(root)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = textName.getText().toString(), number = textNumber.getText().toString();
                            if (name != null && !name.equals(""))
                                if (number != null && !number.equals("")) {
                                    Person p = new Person(number, name);
                                    PersonManager.savePerson(p, context);
                                    toUpdate.setAdapter(new PersonAdapter(context, PersonManager.getSavedPersons(context)));
                                }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
    }

    public void show() {
        dialog.show();
    }
}
