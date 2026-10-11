package com.bootleggers.dumpster.external.themebox;

import com.bootleggers.dumpster.R;

import java.util.HashMap;
import java.util.Map;

public class UIStyles extends BaseThemePicker {

    private final Map<String, String> mTrio = new HashMap<String, String>() {{
        put("com.android.settings", "android.theme.customization.style.settings");
        put("com.android.systemui", "android.theme.customization.style.systemui");
    }};

    @Override
    protected String category() {
        return "android.theme.customization.style.android";
    }

    @Override
    protected String targetPackage() {
        return "android";
    }

    @Override
    protected int titleRes() {
        return R.string.theme_customization_ui_style_title;
    }

    @Override
    protected void applyOverlay(String pkg) {
        super.applyOverlay(pkg);
        // A UI style is an (android, settings, systemui) trio sharing the
        // theme name (4th package segment, e.g. "...theme.shishu...").
        String pattern = ThemeHelper.PKG_DEFAULT.equals(pkg) ? ""
                : pkg.split("\\.")[4];
        for (Map.Entry<String, String> entry : mTrio.entrySet()) {
            String target = entry.getKey();
            String cat = entry.getValue();
            if (pattern.isEmpty()) {
                mThemeHelper.setOverlayEnabled(cat, ThemeHelper.PKG_DEFAULT, target);
                continue;
            }
            for (String p : mThemeHelper.getOverlayPackagesForCategory(cat, target)) {
                if (p.contains(pattern)) {
                    mThemeHelper.setOverlayEnabled(cat, p, target);
                }
            }
        }
    }
}
