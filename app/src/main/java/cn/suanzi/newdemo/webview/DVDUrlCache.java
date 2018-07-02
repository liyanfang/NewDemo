package cn.suanzi.newdemo.webview;

import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import cn.suanzi.newdemo.App;
import cn.suanzi.newdemo.Util.Util;

/**
 * Created by liyanfang on 2017/11/30.
 */
public class DVDUrlCache {
    private static final String LOG_TAG = "DVDUrlCache";


    private static final long ONE_SECOND = 1000L;
    private static final long ONE_MINUTE = 60L * ONE_SECOND;
    public long ONE_HOUR = 60 * ONE_MINUTE;
    public long ONE_DAY = 24 * ONE_HOUR;
    public long ONE_MONTH = 30 * ONE_DAY;


    private static final LinkedHashMap<String, Callable<Boolean>> queueMap = new LinkedHashMap<>();

    private static class CacheEntry {
        public String url;
        public String fileName;
        String mimeType;
        public String encoding;
        long maxAgeMillis;

        private CacheEntry(String url, String fileName,
                           String mimeType, String encoding, long maxAgeMillis) {
            this.url = url;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.encoding = encoding;
            this.maxAgeMillis = maxAgeMillis;
        }
    }


    private Map<String, CacheEntry> cacheEntries = new HashMap<>();
    private File rootDir = null;


    public DVDUrlCache() {
         //本地缓存路径，请在调试中自行修改
        // this.rootDir = DiskUtil.getDiskCacheDir(DVDApplicationContext.getInstance().getApplicationContext());
        this.rootDir = Util.getExternalCacheDir(App.getContext(), "webview");
    }

    public void register(String url, String cacheFileName,
                         String mimeType, String encoding,
                         long maxAgeMillis) {
        CacheEntry entry = new CacheEntry(url, cacheFileName, mimeType, encoding, maxAgeMillis);
        this.cacheEntries.put(url, entry);
    }


    public WebResourceResponse load(final String url) {
        try {
            final CacheEntry cacheEntry = this.cacheEntries.get(url);
            if (cacheEntry == null) {
                return null;
            }
            final File cachedFile = new File(this.rootDir.getPath() + File.separator + cacheEntry.fileName);
            if (cachedFile.exists()) {
                //还没有下载完
                if (queueMap.containsKey(url)) {
                    return null;
                }
                long cacheEntryAge = System.currentTimeMillis() - cachedFile.lastModified();
                if (cacheEntryAge > cacheEntry.maxAgeMillis) {
                    cachedFile.delete();
                    return null;
                }
                return new WebResourceResponse(
                        cacheEntry.mimeType, cacheEntry.encoding, new FileInputStream(cachedFile));
            } else {
                if (!queueMap.containsKey(url)) {
                    queueMap.put(url, new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return downloadAndStore(url, cacheEntry);
                        }
                    });
                    final FutureTask<Boolean> futureTask = ThreadPoolManager.getInstance().addTaskCallback(queueMap.get(url));
                    ThreadPoolManager.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (futureTask.get()) {
                                    queueMap.remove(url);
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                Log.d(LOG_TAG, "", e);
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


    private boolean downloadAndStore(final String url, final CacheEntry cacheEntry)
            throws IOException {
        FileOutputStream fileOutputStream = null;
        InputStream urlInput = null;
        try {
            URL urlObj = new URL(url);
            URLConnection urlConnection = urlObj.openConnection();
            urlInput = urlConnection.getInputStream();
            String tempFilePath = "";
            tempFilePath = DVDUrlCache.this.rootDir.getPath() + File.separator + cacheEntry.fileName + ".temp";
            File tempFile = new File(tempFilePath);
            fileOutputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = urlInput.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.flush();
            File lastFile = new File(tempFilePath.replace(".temp", ""));
            boolean renameResult = tempFile.renameTo(lastFile);
            if (!renameResult) {
            }
//            Log.d(LOG_TAG, "Cache file: " + cacheEntry.fileName + " stored. ");
            return true;
        } catch (Exception e) {
            Log.e(LOG_TAG, "", e);
        } finally {
            if (urlInput != null) {
                urlInput.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        return false;
    }
}
