package labex.feevale.br.looky.utils;

import android.content.Context;
import android.content.SharedPreferences;

import labex.feevale.br.looky.model.User;

/**
 * Created by PabloGilvan on 27/12/2014.
 */
public class SharedPreferencesUtils {

    public static final String MY_SHARED_PREF = "UserShared";

    public static final String KEY_ID = "USER_ID";
    public static final String KEY_LATITUDE = "USER_LAT";
    public static final String KEY_LONGITUDE = "USER_LONGITUDE";
    public static final String KEY_USERNAME = "USER_USERNAME";
    public static final String KEY_EMAIL = "USER_EMAIL";


    public void saveUser(Context context, User user) {
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.putLong(KEY_ID, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERNAME, user.getUserName());
        editor.putString(KEY_LATITUDE, user.getLatitude() + "");
        editor.putString(KEY_LONGITUDE, user.getLongitude() + "");
        editor.commit();
    }

    public SharedPreferences returnMySharedPref(Context context) {
        return context.getSharedPreferences(MY_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public Boolean checkMyPrefs(Context context) {
        SharedPreferences preferences = returnMySharedPref(context);
        Long id = preferences.getLong(KEY_ID, 0);
        if (id > 0)
            return true;
        else
            return false;
    }

    public User getUSer(Context context) {
        SharedPreferences preferences = returnMySharedPref(context);
        if (checkMyPrefs(context)) {
            User user = new User();
            user.setId(preferences.getLong(KEY_ID, 0));
            user.setEmail(preferences.getString(KEY_EMAIL, ""));
            user.setUserName(preferences.getString(KEY_USERNAME, ""));
            try {
                user.setLatitude(Double.parseDouble(preferences.getString(KEY_LATITUDE, "0")));
                user.setLongitude(Double.parseDouble(preferences.getString(KEY_LONGITUDE, "0")));
            }catch (Exception e){
                user.setLatitude(0d);
                user.setLongitude(0d);
            }
            return user;
        }else{
           return null;
        }
    }

    public void clear(Context context){
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.clear();
    }
}
