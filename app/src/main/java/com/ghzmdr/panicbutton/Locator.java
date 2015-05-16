package com.ghzmdr.panicbutton;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import java.io.IOException;
import java.util.Locale;


/**
 * Created by ghzmdr on 12/01/15.
 */
public class Locator implements android.location.LocationListener {

    private Context context;
    private LocationManager lm;
    private Location loc = null;
    private boolean gpsEnabled = false, networkEnabled = false;

    private static Locator ref;

    private Locator(Context context) {
        this.context = context;
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        requestUpdate();
    }

    public void requestUpdate() {
        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsEnabled)
            lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
        else if (networkEnabled)
            lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
    }

    public Location getLocation() throws NoPositionProvidersException {

        String provider = null;
        Location tmpLoc = null;

        if (gpsEnabled) {
            provider = LocationManager.GPS_PROVIDER;
            tmpLoc = lm.getLastKnownLocation(provider);
        }

        if (networkEnabled && tmpLoc == null) {
            provider = LocationManager.NETWORK_PROVIDER;
            tmpLoc = lm.getLastKnownLocation(provider);
        }

        if (provider == null){
            throw new NoPositionProvidersException("Can't find position provider");
        }

        if (tmpLoc != null) {
            if (loc == null || tmpLoc.getAccuracy() > loc.getAccuracy())
                loc = tmpLoc;
        }

        if (loc == null){
            throw new NoPositionProvidersException("Can't find position provider");
        }
        Location ret = new Location("");
        ret.setLatitude(loc.getLatitude());
        ret.setLongitude(loc.getLongitude());
        return ret;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Location services disabled");

        // Setting Dialog Message
        alertDialog.setMessage("Please enable them in the settings");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public static String getAddressFromLocation(Location loc, Context context){
        Geocoder g = new Geocoder(context, Locale.getDefault());
        Address a = null;

        int tries = 5;

        for (int i = 0; i < tries; i++) {
            try {
                a = g.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1).get(0);
                if (a == null) g.getFromLocation(loc.getLatitude(), loc.getLongitude(), i+1).get(i);
            } catch (IOException | IndexOutOfBoundsException ignored) {
            }
        }

        if (a == null) return null;

        String ret = a.getAddressLine(0) + "\n" + a.getAddressLine(1);
        return ret;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class NoPositionProvidersException extends Throwable {
        private NoPositionProvidersException(String detailMessage) {
            super(detailMessage);
        }
    }

    public static Locator getLocator(Context context) {
        if (ref == null) ref = new Locator(context);
        return ref;
    }
}
