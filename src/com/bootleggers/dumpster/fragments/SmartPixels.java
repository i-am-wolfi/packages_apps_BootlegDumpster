/*
 * Copyright (C) 2018 CarbonROM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bootleggers.dumpster.fragments;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;
import androidx.preference.ListPreference;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bootleggers.dumpster.R;
import androidx.preference.PreferenceFragmentCompat;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import java.util.ArrayList;
import java.util.List;
public class SmartPixels extends PreferenceFragmentCompat {
    private static final String TAG = "SmartPixels";
    private static final String SMART_PIXELS_ENABLE = "smart_pixels_enable";
    private static final String SMART_PIXELS_ON_POWER_SAVE = "smart_pixels_on_power_save";

    private SmartPixelsObserver mSmartPixelsObserver;
    private SwitchPreference mSmartPixelsEnabled;
    private SwitchPreference mSmartPixelsPowerSave;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.smart_pixels, rootKey);
        mSmartPixelsEnabled = (SwitchPreference) findPreference(SMART_PIXELS_ENABLE);
        mSmartPixelsPowerSave = (SwitchPreference) findPreference(SMART_PIXELS_ON_POWER_SAVE);

        mSmartPixelsObserver = new SmartPixelsObserver(new Handler());
    }

    private void updateSwitchPreferences() {
        final ContentResolver resolver = getActivity().getContentResolver();
        if (mSmartPixelsEnabled != null) {
            boolean spEnabled = Settings.System.getIntForUser(resolver,
                "smart_pixels_enable", 0, UserHandle.USER_CURRENT) == 1;
            mSmartPixelsEnabled.setChecked(spEnabled);
        }

        if (mSmartPixelsPowerSave != null) {
            boolean spEnabledOnPS = Settings.System.getIntForUser(resolver,
                "smart_pixels_on_power_save", 0, UserHandle.USER_CURRENT) == 1;
            mSmartPixelsPowerSave.setChecked(spEnabledOnPS);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mSmartPixelsObserver != null) {
            mSmartPixelsObserver.register();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSmartPixelsObserver != null) {
            mSmartPixelsObserver.unregister();
        }
    }

    private class SmartPixelsObserver extends ContentObserver {
        public SmartPixelsObserver(Handler handler) {
            super(handler);
        }

        public void register() {
            getActivity().getContentResolver().registerContentObserver(Settings.System.getUriFor(
                    SMART_PIXELS_ENABLE), false, this, UserHandle.USER_CURRENT);
            getActivity().getContentResolver().registerContentObserver(Settings.System.getUriFor(
                    SMART_PIXELS_ON_POWER_SAVE), false, this, UserHandle.USER_CURRENT);
        }

        public void unregister() {
            getActivity().getContentResolver().unregisterContentObserver(this);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            updateSwitchPreferences();
        }
    }

                @Override

                @Override
}
