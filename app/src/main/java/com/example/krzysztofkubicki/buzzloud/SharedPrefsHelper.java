package com.example.krzysztofkubicki.buzzloud;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by krzysztof.kubicki on 21/04/17.
 */

public class SharedPrefsHelper {

    private static final String SHOULD_UNMUTE = "shouldUnmute";
    private static final String PREFS_FILE = "PREFS_FILE";
    private SharedPreferences sharedPreferences;

    public SharedPrefsHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_FILE, 0);
    }


    public boolean shouldUnmuteWhileSmsArrives() {
        return sharedPreferences.getBoolean(SHOULD_UNMUTE, false);
    }

    public void setUnmuteUponIncommingSMS(boolean shouldUnmute) {

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(SHOULD_UNMUTE, shouldUnmute);
        edit.apply();
    }
}
