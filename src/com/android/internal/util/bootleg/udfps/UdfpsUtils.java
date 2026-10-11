package com.android.internal.util.bootleg.udfps;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;

public class UdfpsUtils {
    public static boolean hasUdfpsSupport(Context context) {
        FingerprintManager fm = context.getSystemService(FingerprintManager.class);
        if (fm == null) return false;
        try {
            for (FingerprintSensorPropertiesInternal prop : fm.getSensorPropertiesInternal()) {
                if (prop.isAnyUdfpsType()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
