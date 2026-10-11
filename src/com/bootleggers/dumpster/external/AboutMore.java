package com.bootleggers.dumpster.external;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.Vibrator;
import androidx.preference.PreferenceCategory;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import android.text.TextUtils;

import com.bootleggers.dumpster.R;

import androidx.preference.PreferenceFragmentCompat;

import com.android.internal.logging.nano.MetricsProto;

public class AboutMore extends PreferenceFragmentCompat {

    private Preference prefCrowd;
    private String bootlegCrowdinString;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.bootleg_external_frag_aboutmore, rootKey);
        Resources res = getResources();
        prefCrowd = (Preference) findPreference("bootleg_morecd");

        bootlegCrowdinString = res.getString(R.string.bootleg_moarstuff_cd_sumgen, res.getString(R.string.bootleg_moarstuff_cd_summary01), res.getString(R.string.bootleg_moarstuff_cd_summary02));

        prefCrowd.setSummary(String.valueOf(bootlegCrowdinString));
    }


}