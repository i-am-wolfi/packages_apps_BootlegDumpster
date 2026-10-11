package com.bootleggers.dumpster.external.themebox;

import com.bootleggers.dumpster.R;

public class IconShapes extends BaseThemePicker {
    @Override
    protected String category() {
        return "android.theme.customization.adaptive_icon_shape";
    }

    @Override
    protected String targetPackage() {
        return "android";
    }

    @Override
    protected int titleRes() {
        return R.string.theme_customization_icon_shape_title;
    }
}
