package com.android.internal.util.bootleg;

import android.content.Context;
import android.content.pm.PackageManager;

public class BootlegUtils {
    public static boolean isPackageInstalled(Context context, String pkg) {
        try {
            context.getPackageManager().getPackageInfo(pkg, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
