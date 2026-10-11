package com.bootleggers.dumpster.external.themebox;

import com.bootleggers.dumpster.R;

public class StatusBarIcons extends BaseThemePicker {
    @Override
    protected String category() {
        return "android.theme.customization.icon_pack.android";
    }

    @Override
    protected String targetPackage() {
        return "android";
    }

    @Override
    protected int titleRes() {
        return R.string.theme_customization_icon_pack_title;
    }
}
