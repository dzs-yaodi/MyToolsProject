package com.xw.baselib.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by scorpio on 2016/11/3.
 */

public class Tools {

    /**
     * 获取软件版本号 （1）
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取软件版本名称（1.0.0）
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 数字保留小数点后两位
     */
    public static String valueFormat(Object price) {
        try {
            double dou =Double.parseDouble(price.toString());
            if (dou == 0) {
                return "0.00";
            }
            DecimalFormat df2 = new DecimalFormat("##0.00");// 保留2位
            return df2.format(dou);
        } catch (Exception e) {
            return "0.00";
        }

    }

    /**
     * 数字保留小数点后两位
     *
     * @param rule
     */
    public static String valueFormat(Object price, String rule) {
        String format = "##0.00";
        if (!TextUtils.isEmpty(rule)) {
            format = rule;
        }
        try {
            double dou = Double.parseDouble(price.toString());
            if (dou == 0) {
                return "0.00";
            }
            DecimalFormat df2 = new DecimalFormat(format);// 保留2位
            return df2.format(dou);
        } catch (Exception e) {
            return "0.00";
        }

    }

    /**
     * 不保留小数点
     */
    public static String noPointFormat(Object price) {
        try {
            double dou = Double.parseDouble(price.toString());
            if (dou == 0) {
                return "0";
            }
            DecimalFormat df2 = new DecimalFormat("##0");// 保留2位
            return df2.format(dou);
        } catch (Exception e) {
            return "0";
        }

    }

    /**
     * 把字符串转为字符数组/
     *
     * @param str
     * @return
     */

    public static String[] StrToArray(String str) {
        String[] str_array = new String[str.length()];
        for (int cnt = 0; cnt < str.length(); cnt++) {
            if (cnt < str.length() - 1) {
                str_array[cnt] = str.substring(cnt, cnt + 1);
            } else {
                str_array[cnt] = str.substring(cnt);
            }

        }
        return str_array;
    }

    /**
     * Base64加密
     *
     * @param xml
     * @return
     */
    @SuppressLint("NewApi")
    public static String encodeBase64(String xml) {
        return Base64.encodeToString(xml.getBytes(), Base64.DEFAULT);
    }

    /**
     * Base64解密
     *
     * @param rexml
     * @return
     */
    @SuppressLint("NewApi")
    public static String decodeBase64(String rexml) {
        byte b[] = Base64.decode(rexml, Base64.DEFAULT);
        String reString = new String(b);
        return reString;
    }

    /**
     * 验证是否为邮箱
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern
                .compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为手机号
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 验证是否为固定电话
     */
    public static boolean isPhone(String str) {
        Pattern pattern = Pattern.compile("^(\\d{4}|\\d{3}|\\d{2}|\\d{0})-?\\d{7,8}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证是否为邮编
     */
    public static boolean isPostCode(String str) {
        Pattern pattern = Pattern.compile("^[1-9]\\d{5}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否是身份证
     * @param idCard
     * @return
     */
    public static boolean isIDCard(String idCard){

        String str = "[1-9]{1}[0-9]{5}(19|20)[0-9]{2}"
                + "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
                + "[0-9]{3}[0-9xX]{1}";

        String str1 = "[1-9]{1}[0-9]{5}[0-9]{2}"
                + "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
                + "[0-9]{3}";

        Pattern pattern = Pattern.compile(str);
        Pattern pattern1 = Pattern.compile(str1);

        if (pattern.matcher(idCard).matches() || pattern1.matcher(idCard).matches()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断是否是中国移动
     * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成，
     * 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,中国电信为03；MSIN为移动客户识别码，采用等长11位数字构成。
     *
     * @param context
     * @return
     */
    public static boolean isChianMM(Context context) {
        boolean isChina = false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imsi = telephonyManager.getSubscriberId();
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                isChina = true;
            }
        } else {
            isChina = false;
        }
        return isChina;
    }

    /**
     * 检测网络是否有效
     *
     * @param context
     * @return ture 有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络状态
     *
     * @return 1:无线网  2：手机网络   3：无网络连接
     */
    public static int getNetState(Context context) {
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            // 手机网络连接成功
            return 2;
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            // 手机没有任何的网络
            return 3;
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            // 无线网络连接成功
            return 1;
        }
        return 3;
    }

    /**
     * 匹配用户名：只能为数字、字母或者汉字,4-20
     */
    public static boolean CheckUsername(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z\u4e00-\u9fa5]{4,20}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 根据包名 和类名 打开应用程序
     *
     * @param pkg
     * @param cls
     */
    public static void startapp(Context context, String pkg, String cls) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName componentName = new ComponentName(pkg, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
            try {
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }


    /**
     * 权限 6.0 检测权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkoutPermissions(final Context context, String[] permissions) {
        // 进行权限请求
        boolean result = true;
        int count = 0;
        for (int i = 0; i < permissions.length; i++) {
            final String permission = permissions[i];
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                count = count + 1;
            }
        }
        if (count == permissions.length) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * 清除应用所有的缓存数据
     * @param context
     */
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    /**
     * 判断是否在Wifi网络状态下
     * @param
     * @return
     */
    public boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
