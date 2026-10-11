package com.bootleggers.dumpster;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class MainActivity extends AppCompatActivity
        implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        if (b == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new MainDump()).commit();
        }
    }
    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        Fragment f = getSupportFragmentManager().getFragmentFactory()
                .instantiate(getClassLoader(), pref.getFragment());
        f.setArguments(pref.getExtras());
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, f).addToBackStack(null).commit();
        return true;
    }
}
