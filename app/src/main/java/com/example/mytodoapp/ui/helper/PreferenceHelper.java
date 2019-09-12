package com.example.mytodoapp.ui.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private Context mContext;

    public PreferenceHelper(Context mContext) {
        this.mContext = mContext;
    }

    private SharedPreferences getAppPreference() {
        return mContext.getSharedPreferences( Constants.PREF_GLOBAL, Context.MODE_PRIVATE );
    }

    private SharedPreferences.Editor getEditor() {
        return getAppPreference().edit();
    }

    public boolean isDbLoadedFirstTime() {
        return getAppPreference().getBoolean( Constants.PREF_FIRST_TIME_DB, false );
    }

    public void setDbIsLoadedFirstTime() {
        getEditor().putBoolean( Constants.PREF_FIRST_TIME_DB, true ).apply();
    }

}
