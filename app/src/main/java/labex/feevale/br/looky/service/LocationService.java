package labex.feevale.br.looky.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.PreferencesHelp;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;


public class LocationService implements  LocationListener{

    Context context;

    public LocationService(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        SharedPreferencesUtils preferencesUtils = new SharedPreferencesUtils();
        User user = preferencesUtils.getUSer(context);
        if(user != null) {
            user.setLatitude(location.getLatitude());
            user.setLongitude(location.getLongitude());
            preferencesUtils.saveUser(context, user);
            NotifyPositionService notifyPositionService = new NotifyPositionService(context);
            notifyPositionService.execute();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}