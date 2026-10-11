package com.bootleggers.dumpster.external.themebox;

import com.bootleggers.dumpster.R;

public class FontsPicker extends BaseThemePicker {
    @Override
    protected String category() {
        return "android.theme.customization.font";
    }

    @Override
    protected String targetPackage() {
        return "android";
    }

    @Override
    protected int titleRes() {
        return R.string.theme_customization_font_title;
    }
}
