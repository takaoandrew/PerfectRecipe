package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.utilities;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by andrewtakao on 1/17/18.
 */

public class ColorUtil {
    public static int getTransparentColorFromPosition(float position, float total) {
        String transparency = Integer.toHexString((int)(256-255*(position)/(total)));
        if (transparency.length() == 1) {
            transparency = "0"+transparency;
        }
        return Color.parseColor("#"+transparency+"FFFFFF");
    }

    public static int getTransparentColorFromInversePosition(float position, float total) {
        String transparency = Integer.toHexString((int)(255*(position)/(total)));
        if (transparency.length() == 1) {
            transparency = "0"+transparency;
        }
        return Color.parseColor("#"+transparency+"FFFFFF");
    }
}
