package labex.feevale.br.looky.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import labex.feevale.br.looky.utils.PreferencesHelp;


public class LocationService implements  LocationListener{

    Context context;

    public LocationService(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        PreferencesHelp preferencesHelp = new PreferencesHelp(context);
        preferencesHelp.SetPositionUser(location.getLatitude(),location.getLongitude());

        NotifyPositionService notifyPositionService = new NotifyPositionService(context);
        notifyPositionService.execute();

        Toast.makeText(context,"notificou",Toast.LENGTH_LONG).show();
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