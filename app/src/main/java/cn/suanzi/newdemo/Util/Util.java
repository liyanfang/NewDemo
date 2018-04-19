package cn.suanzi.newdemo.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Environment;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by liyanfang on 2017/11/28.
 */
public class Util {

    /**
     * dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        try{
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Exception e) {
            return (int) dpValue;
        }
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 检测本地网络是否打开
     * @return true：本地网络开启；false：本地网络关闭。
     */
    public static boolean isNetworkOpen(Context ctx) {
        try{
            if (ctx != null) {
                ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connManager != null && connManager.getActiveNetworkInfo() != null) {
                    return connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable();
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String getResourcesFileName(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    @SuppressLint("NewApi")
    public static File getExternalCacheDir(Context context, String filePath) {
        File file = null;
        if (android.os.Build.VERSION.SDK_INT > 7) {
            file = context.getApplicationContext().getExternalFilesDir(filePath);
        } else {
            file = new File(Environment.getExternalStorageDirectory() + filePath);
            // 如果目录不存在就创建一个
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        if (file == null || !file.canWrite()) {
            file = context.getApplicationContext().getCacheDir();
        }
        return file;
    }
}
