package com.xw.baselib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/11/17.
 */
public class UITools {

    private static Toast strShorttoast; // 短，字符串提示
    private static Toast strLongtoast; // 长，字符串提示

     /**
     * 短 字符串提示
     */
    public static void toastShowShort(Context context, String mes) {
        if (TextUtils.isEmpty(mes)){
            return;
        }
        if (strShorttoast != null) {
            strShorttoast.setText(mes);
            strShorttoast.setDuration(Toast.LENGTH_SHORT);
        } else {
            strShorttoast = Toast.makeText(context, mes, Toast.LENGTH_SHORT);
        }
        strShorttoast.show();
    }

    /**
     * 长 字符串提示
     */
    public static void toastShowLong(Context context, String mes) {
        if (TextUtils.isEmpty(mes)){
            return;
        }
        if (strLongtoast != null) {
            strLongtoast.setText(mes);
            strLongtoast.setDuration(Toast.LENGTH_SHORT);
        } else {
            strLongtoast = Toast.makeText(context, mes, Toast.LENGTH_SHORT);
        }

        strLongtoast.show();
    }

    /*
    图片变字节流

     */

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取时间 年月 周
     *
     * @return
     */
    public static String getWeekStr(int day) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK) + day);
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "周" + mWay;
    }

    /**
     * 把分钟换算为天,小时,分钟的方法
     * 4天23小时42分钟以前
     *
     * @param time
     * @return
     */
    public static String getDetailTime(int time) {
        int day = time / 1440;
        int hour = (time % 1440) / 60;
        int min = (time % 1440) % 60;
        String detailTime;
        if (day == 0 && hour == 0 && min == 0) {
            detailTime = "刚刚";
            return detailTime;
        }
        if (day == 0 && hour == 0) {
            detailTime = min + "分钟前";
            return detailTime;
        } else if (day == 0) {
            detailTime = hour + "小时前";
            return detailTime;
        } else if (day == 1) {
            detailTime = "昨天" + hour + ":" + min;
            return detailTime;
        } else if (day == 2) {
            detailTime = "前天" + hour + "小时" + min + "分钟前";
            return detailTime;
        } else if (day > 2) {
            detailTime = day + " " + hour + "-" + min;
            return detailTime;
        }
        return null;
    }

    /**
     * 计算两个时间相差的分钟数
     * time1-需要计算的时间，time2-当前时间
     *
     * @return
     */
    public static String getTimeMinuteDiffer(String time1, String time2) {
        int time = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//时间形式

        try {
            long date = (format.parse(time1)).getTime();
            long now = (format.parse(time2)).getTime();

            time = (int) ((now - date) / (1000 * 60));
            time = Math.abs(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = time / 1440;
        int hour = (time % 1440) / 60;
        int min = (time % 1440) % 60;
        String detailTime;
        String[] sellerTime = time1.split(" ");
        String passTime = "未知时间";
        if (sellerTime.length >= 2) {
            passTime = sellerTime[1];
        }
        if (day == 0 && hour == 0 && min == 0) {
            detailTime = "刚刚";
            return detailTime;
        }
        if (day == 0 && hour == 0) {
            detailTime = min + "分钟前";
            return detailTime;
        }
        if (day == 0) {
            detailTime = hour + "小时前";
            return detailTime;
        }
        if (day == 1) {
            detailTime = "昨天 " + passTime.substring(0, 5);
            return detailTime;
        }
        if (day == 2) {
            detailTime = "前天 " + passTime.substring(0, 5);
            return detailTime;
        }

        if (day > 2) {
            detailTime = time1.substring(0, 16);
            return detailTime;
        }
        return null;
    }

    /**
     * dp转px单位
     *
     * @param context context
     * @param dpValue dp的值
     * @return dp转化成px后的值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 初始化状态栏，适配沉浸式
     */
    public static void initTitleColorBar(Activity activity, View bgView,String coclor) {
        //设置沉浸式状态栏
        translucentStatusBar(activity, true);
        //计算出状态栏高度，并设置view留出对应位置
        int height = (int) getStatusBarHeight(activity);
        bgView.setPadding(0, height, 0, 0);
        bgView.setBackgroundColor(Color.parseColor(coclor));
    }

    /**
     * 初始化状态栏，适配沉浸式
     */
    public static void initTitleBarBitMap(Activity activity, View bgView,Bitmap bitmap) {
        //设置沉浸式状态栏
        translucentStatusBar(activity, true);
        //计算出状态栏高度，并设置view留出对应位置
        int height = (int) getStatusBarHeight(activity);
        bgView.setPadding(0, height, 0, 0);

        if (bitmap != null) {
            bgView.setBackground(new BitmapDrawable(activity.getResources(), bitmap));
        }
    }

    @SuppressLint("NewApi")
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (hideStatusBarBackground) {
            //如果为全透明模式，取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置状态栏为透明
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置window的状态栏不可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            //如果为半透明模式，添加设置Window半透明的Flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        //view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.requestApplyInsets(mChildView);
        }
    }


    /**
     * 获取状态栏高度。
     *
     * @param context context
     * @return 返回获取的状态栏高度
     */
    public static float getStatusBarHeight(Context context) {
        float result = 0;
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimension(resourceId);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）

        return width;
    }
}
