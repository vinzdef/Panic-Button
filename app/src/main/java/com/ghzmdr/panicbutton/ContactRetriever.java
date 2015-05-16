package com.ghzmdr.panicbutton;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ghzmdr on 18/01/15.
 */
public class ContactRetriever {
    private final String TAG = "ContactRetriever";

    private ContentResolver cr;
    private Context context;
    private Uri contractData;
    private String id;

    public ContactRetriever(Context context, Uri contractData) {
        this.context = context;
        this.cr = context.getContentResolver();
        this.contractData = contractData;
    }

    public Person getPerson() {
        String name = getName();
        String num = getNumber();

        if (name != null && num != null)
            return new Person(getNumber(), getName());
        else return null;
    }


    private String getNumber() {
        String ret = null;
        Cursor cId = cr.query(contractData, new String[]{ContactsContract.Contacts._ID}, null, null, null);

        if (cId.moveToFirst()){
            id = cId.getString(cId.getColumnIndex(ContactsContract.Contacts._ID));
            Log.i(TAG + " IDs: ", id);
        }
        cId.close();

        Cursor cNum = cr.query(contractData, null, null, null, null);

        if (cNum.moveToNext()){
            ret = cNum.getString(cNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.i(TAG + " NUMBERS: ", ret);
        }
        return ret;
    }

    private String getName() {
        String ret = null;

        Cursor c = cr.query(contractData, null, null, null, null);
        if (c.moveToFirst())
            ret = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        c.close();
        Log.i(TAG + "NAMES: ", ret);
        return ret;
    }
}
