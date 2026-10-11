/*
 * Copyright (C) 2022 crDroid Android Project
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

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.bootleg.BootlegUtils;

import com.bootleggers.dumpster.R;
import androidx.preference.PreferenceFragmentCompat;

public class UdfpsSettings extends PreferenceFragmentCompat {

    private static final String UDFPS_ANIM_PREVIEW = "udfps_recognizing_animation_preview";

    private Preference mUdfpsAnimPreview;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.udfps_settings, rootKey);

        final PreferenceScreen prefScreen = getPreferenceScreen();
        Resources resources = getResources();

        final boolean udfpsResPkgInstalled = BootlegUtils.isPackageInstalled(getContext(),
                "com.bootleggers.udfps.resources");
        mUdfpsAnimPreview = findPreference(UDFPS_ANIM_PREVIEW);
        if (!udfpsResPkgInstalled) {
            prefScreen.removePreference(mUdfpsAnimPreview);
        }
    }

    public static void reset(Context mContext) {
        ContentResolver resolver = mContext.getContentResolver();
        Settings.System.putIntForUser(resolver,
                "udfps_anim", 0, UserHandle.USER_CURRENT);
        Settings.System.putIntForUser(resolver,
                "udfps_anim_style", 0, UserHandle.USER_CURRENT);
        Settings.System.putIntForUser(resolver,
                "udfps_icon", 0, UserHandle.USER_CURRENT);
    }

}
