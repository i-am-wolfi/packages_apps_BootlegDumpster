package com.android.internal.util.bootleg.udfps;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;

public class UdfpsUtils {
    public static boolean hasUdfpsSupport(Context context) {
        FingerprintManager fm = context.getSystemService(FingerprintManager.class);
        if (fm == null) return false;
        for (android.hardware.biometrics.SensorProperties prop : fm.getSensorPropertiesInternal()) {
            if (prop.getSensorType() == android.hardware.biometrics.BiometricAuthenticator.TYPE_UNDER_DISPLAY) {
                return true;
            }
        }
        return false;
    }
}
