package com.example.krzysztofkubicki.buzzloud;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by krzysztof.kubicki on 21/04/17.
 */

public class SharedPrefsHelper {

    private static final String SHOULD_UNMUTE = "shouldUnmute";
    private static final String PREFS_FILE = "PREFS_FILE";
    private static final String UNMUTE_KEYWORD = "UNMUTE_KEYWORD";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefsHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_FILE, 0);
        this.editor = sharedPreferences.edit();
    }


    public boolean shouldUnmuteWhileSmsArrives() {
        return sharedPreferences.getBoolean(SHOULD_UNMUTE, false);
    }

    public void setUnmuteUponIncommingSMS(boolean shouldUnmute) {
        editor.putBoolean(SHOULD_UNMUTE, shouldUnmute);
        editor.apply();
    }

    public void addUnmuteKeyword(String keyword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UNMUTE_KEYWORD, keyword);
        editor.apply();
    }

    public String getUnmuteKeyword() {
        return sharedPreferences.getString(UNMUTE_KEYWORD, "Default keyword is wtfru" );
    }
}
