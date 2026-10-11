package com.bootleggers.dumpster.fragments;

import com.android.internal.logging.nano.MetricsProto;
import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import com.bootleggers.dumpster.R;

import java.util.Arrays;
import java.util.HashSet;

import androidx.preference.PreferenceFragmentCompat;

public class RecentsSettings extends PreferenceFragmentCompat implements
        OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle icicle, String rootKey) {

        setPreferencesFromResource(R.xml.bootleg_dumpster_frag_recents, rootKey);

        ContentResolver resolver = getActivity().getContentResolver();

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        switch (preference.getKey()) {
            default:
                return false;
        }
    }

}
