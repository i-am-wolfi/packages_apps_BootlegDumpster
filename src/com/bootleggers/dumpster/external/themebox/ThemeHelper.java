/*
 * Copyright (C) 2025 The BootleggersROM Project
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

package com.bootleggers.dumpster.external.themebox;

import android.content.Context;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManager;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * Small OverlayManager wrapper replacing the A13
 * com.android.internal.util.bootleg.ThemeUtils helper, which was never
 * ported to A16. All calls run as the platform-signed Dumpster app, which
 * holds the privileges needed for overlay changes.
 */
public class ThemeHelper {

    /** Fake entry representing "no overlay" (stock look). */
    public static final String PKG_DEFAULT = "android";

    private final Context mContext;
    private final OverlayManager mOm;
    private final PackageManager mPm;
    private final UserHandle mUser;

    public ThemeHelper(Context context) {
        mContext = context;
        mOm = context.getSystemService(OverlayManager.class);
        mPm = context.getPackageManager();
        mUser = UserHandle.of(UserHandle.myUserId());
    }

    /** All overlay packages registered for category/target, "android" first. */
    public List<String> getOverlayPackagesForCategory(String category, String target) {
        List<String> pkgs = new ArrayList<>();
        pkgs.add(PKG_DEFAULT);
        if (mOm == null) return pkgs;
        try {
            for (OverlayInfo info : mOm.getOverlayInfosForTarget(target, mUser)) {
                if (category.equals(info.category)
                        && !pkgs.contains(info.packageName)) {
                    pkgs.add(info.packageName);
                }
            }
        } catch (Exception e) {
            // Overlay service hiccup: still offer Default.
        }
        return pkgs;
    }

    /** Currently enabled package in category/target, or "android". */
    public String getEnabledPkg(String category, String target) {
        if (mOm == null) return PKG_DEFAULT;
        try {
            for (OverlayInfo info : mOm.getOverlayInfosForTarget(target, mUser)) {
                if (category.equals(info.category) && info.isEnabled()) {
                    return info.packageName;
                }
            }
        } catch (Exception e) {
        }
        return PKG_DEFAULT;
    }

    /**
     * Enable pkg exclusively in category (disable everything else).
     * "android" disables the whole category back to stock.
     */
    public void setOverlayEnabled(String category, String pkg, String target) {
        if (mOm == null) return;
        try {
            if (PKG_DEFAULT.equals(pkg)) {
                for (String p : getOverlayPackagesForCategory(category, target)) {
                    if (!PKG_DEFAULT.equals(p)) {
                        mOm.setEnabled(p, false, mUser);
                    }
                }
            } else {
                mOm.setEnabledExclusiveInCategory(pkg, mUser);
            }
        } catch (Exception e) {
        }
    }

    public String getLabel(String pkg) {
        if (PKG_DEFAULT.equals(pkg)) return "Default";
        try {
            CharSequence label = mPm.getApplicationInfo(pkg, 0).loadLabel(mPm);
            if (label != null) return label.toString();
        } catch (PackageManager.NameNotFoundException e) {
        }
        return pkg;
    }
}
