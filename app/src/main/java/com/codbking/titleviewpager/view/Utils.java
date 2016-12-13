package com.codbking.titleviewpager.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;

/**
 * Created by wulang on 2016/12/13.
 */

 class Utils {

    public static ColorStateList createColorStateList(Context context, int nColorRes, int pColorRes) {
        int pressed = context.getResources().getColor(pColorRes);
        int normal = context.getResources().getColor(nColorRes);

        int[] colors = new int[]{pressed, pressed, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static int getColor(Context context,int desR) {
        return context.getResources().getColor(desR);
    }

    public static int px(int dip){
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int getScreemWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }


}
