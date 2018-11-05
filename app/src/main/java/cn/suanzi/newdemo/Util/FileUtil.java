package cn.suanzi.newdemo.Util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by liyanfang on 2018/8/20.
 */

public class FileUtil {
    /**
     * 获取app缓存目录
     * @param fileName 缓存文件名称
     * @return
     */
    public static File getCacheFile (Context context, String fileName) {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = context.getExternalFilesDir(fileName);
        }
        if (file == null || !file.canWrite()) {
            file = new File(context.getCacheDir(), fileName);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取app缓存目录
     * @return
     */
    public static File getCacheFilePath (Context context) {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = context.getExternalCacheDir();
        }
        return file;
    }
}
