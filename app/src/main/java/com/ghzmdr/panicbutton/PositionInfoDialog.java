package com.ghzmdr.panicbutton;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ghzmdr on 19/01/15.
 */
public class PositionInfoDialog extends Dialog{
    public PositionInfoDialog(Location l, Context context) {
        super(context);
        setTitle("You are here:");

        TextView textLat = new TextView(context);
        textLat.setTextSize(20);
        textLat.setText("Latitude: " + l.getLatitude());

        TextView textLon = new TextView(context);
        textLon.setTextSize(20);
        textLon.setText("Longitude: " + l.getLongitude());

        TextView textAddr = new TextView(context);
        textAddr.setTextSize(20);
        textAddr.setText("Address: " + Locator.getAddressFromLocation(l, context));

        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(30,30,30,30);


        ll.addView(textLat);
        ll.addView(textLon);
        ll.addView(textAddr);

        setContentView(ll);
        setCancelable(true);
    }


}
