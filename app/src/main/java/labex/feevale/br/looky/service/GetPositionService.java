package labex.feevale.br.looky.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

public class GetPositionService extends Service {

    private static final int FIVE_MINUTES = 1000 * 60 * 5;
    private static final int ZERO_METERS = 0;
    public LocationManager locationManager;
    public LocationService listener;

    public GetPositionService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationService(GetPositionService.this);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, FIVE_MINUTES, ZERO_METERS, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, FIVE_MINUTES, ZERO_METERS, listener);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

