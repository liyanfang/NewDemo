package cn.suanzi.newdemo.api;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import cn.suanzi.newdemo.Util.AppMD5Util;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * 请求API
 * Created by liyanfang on 2016/5/31.
 */
public final class RetrofitUtil {
    private static final String TAG = RetrofitUtil.class.getSimpleName();
    public final static String URL = "https://testapi.tatagou.com.cn:20446/v2/";
    /**
     * 5秒超时
     */
    public static final int TIMEOUT_5 = 5000;
    /**
     * 3秒超时
     */
    public static final int TIMEOUT_10 = 10000;
    private Retrofit retrofit;
    /**
     * 缓存大小5M
     */
    private static int cacheSize = 1024 * 1024 * 10;
    private static File cacheFile;
    private static Cache cache;
    private static OkHttpClient okHttpClient;

    /**
     * 获取ApiService实例
     *
     * @return
     */
    public static RetrofitUtil getInstance() {
        if (okHttpClient == null) {
            initOkHttpClient(TIMEOUT_5, TIMEOUT_10, false);
        }
        return ApiHolder.retrofitUtil;
    }

    static class ApiHolder {
        private static final RetrofitUtil retrofitUtil = new RetrofitUtil();
    }

    public RetrofitUtil() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .build();
    }

    public RetrofitUtil resetTimeout(long connectTime, long writeTime, boolean isRetry) {
        initOkHttpClient(connectTime, writeTime, isRetry);
        return new RetrofitUtil();
    }

    public static RetrofitUtil initOkHttpClient(long connectTime, long writeTime, boolean isRetry) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                String urlKey = chain.request().url().toString();
                HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();
                urlBuilder.addQueryParameter("source", "PINGJIA") // 来源
                        .addQueryParameter("appVersion", "3.4.0.0") // sdk版本
//                            .addQueryParameter("dt",Util.getAndroidID(TtgSDK.getContext())) // androidID
//                            .addQueryParameter("deviceId",Util.phoneImei(TtgSDK.getContext())) // imei
                        .addQueryParameter("sv", "3.4.0") // 宿主APP版本
                        .addQueryParameter("pf", "ANDROID") // 平台代号
                        .addQueryParameter("v", "2.2.5") // api的版本号
                        .addQueryParameter("sysv", String.valueOf(Build.VERSION.SDK_INT)) // 手机系统版本号
                        .addQueryParameter("pid", "000000000"); // mamaPid推广位
                builder.url(urlBuilder.build())
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("content_encoding", "gzip");
                Request request = builder.build();
                HttpUrl httpUrl = request.url();
                URI uri = httpUrl.uri();
                String signInfo = getSignInfo(uri.toString());// 获取sign签名
                request = builder.url(urlBuilder.addQueryParameter("sign", signInfo).build()).build();
                HttpUrl httpUrl2 = request.url();
                URI uri2 = httpUrl2.uri();
                Log.d(TAG, "okhttp uri = " + uri2.toString());
                // 设置
                if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                    request = request.newBuilder().addHeader("Connection", "close").build();
                }
                Response originalResponse = chain.proceed(request);
                Response.Builder responseBuilder = originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .addHeader("CONTENT_ENCODING", "gzip")
                        .addHeader("CONTENT_TYPE", "application/json");
//                if (Util.isNetworkOpen(TtgSDK.getContext()) && TtgSDK.getContext() != null) {
                // 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return responseBuilder.header("Cache-Control", cacheControl).build();
//                } else {
//                    // 没有网络的时候，只从缓存中读取数据
//                    return responseBuilder.header("Cache-Control", "public, only-if-cached, max-stale=2419200").build();
//                }
            }
        };
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(connectTime, TimeUnit.MILLISECONDS) // 设置超时
                .readTimeout(writeTime, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTime, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(isRetry)  //错误重连
                .addNetworkInterceptor(interceptor);
        okHttpClient = okHttpClientBuilder.build();
        return ApiHolder.retrofitUtil;
    }

    /**
     * 删除缓存
     */
    public static void clearCache() {
        try {
            if (okHttpClient != null && okHttpClient.cache() != null) {
                okHttpClient.cache().evictAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 签名API请求参数
     *
     * @param url
     * @return
     */
    private static String getSignInfo(String url) {
        StringBuffer signBuffer = new StringBuffer();
        try {
            Uri uri = Uri.parse(url);
            Set<String> parameterNameSet = uri.getQueryParameterNames();
            if (parameterNameSet != null && parameterNameSet.size() > 0) {
                TreeSet<String> ts = new TreeSet<String>();
                ts.addAll(parameterNameSet);
                Iterator<String> iterator = ts.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = uri.getQueryParameter(key);
                    signBuffer.append(Uri.encode(value));
                }
            }
            return AppMD5Util.MD5(signBuffer.append("TATAGOU&&&9dot9").toString());
        } catch (Exception e) {
        }
        return signBuffer.toString();
    }

    /**
     * ApiService
     *
     * @return
     */
    public <T> T getService(final Class<T> service) {
        return retrofit.create(service);
    }
}
