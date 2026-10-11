package com.bootleggers.dumpster.external.themebox;

import com.bootleggers.dumpster.R;

public class LockClockFonts extends BaseThemePicker {
    @Override
    protected String category() {
        return "android.theme.customization.lockscreen_clock_font";
    }

    @Override
    protected String targetPackage() {
        return "android";
    }

    @Override
    protected int titleRes() {
        return R.string.theme_customization_lock_clock_title;
    }
}
