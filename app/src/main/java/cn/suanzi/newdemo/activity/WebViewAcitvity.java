package cn.suanzi.newdemo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.Util;
import cn.suanzi.newdemo.webview.DVDUrlCache;

/**
 * webview 离线下载测试
 * Created by liyanfang on 2017/11/30.
 */
public class WebViewAcitvity extends FragmentActivity{


    private WebView mWebView;

    private FrameLayout mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);
        mWebView = new WebView(getApplicationContext());
        setmWebView(mWebView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(webChromeClient);
        mRootView = (FrameLayout) findViewById(R.id.ttg_fy_content);
        mRootView.addView(mWebView);
        dvdUrlCache = new DVDUrlCache();
        mWebView.loadUrl("https://cdnbd.tatagou.com.cn/ttjx-contents/hybrid-native/special-hot/index.html?spId=201715&ttgTrack=platform%3DANDROID%26v%3D0.0.8%26deviceId%3D99000535972385%26source%3DQLDS%26appVersion%3D3.2.0.0%26pid%3D65048585%26appDeviceId%3D0000000000000000c59de0a49569a264%26ip%3D115.236.171.58%26pf%3DANDROID%26sv%3D60271066%26dt%3Dc59de0a49569a264&ttgdecode=1&userId=");
        Log.d("TTG", "shouldOverrideUrlLoading: init");

    }

    /**
     * webview代理
     */
    WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String urlConection) {
            return super.shouldOverrideUrlLoading(view, urlConection);
           }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            getRespone(url.toString());

        }

        // 复写shouldInterceptRequest
        //API21以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.d("TTG", "shouldInterceptRequest: " + request.getUrl().toString());
            return getCacheRe(request.getUrl().toString());
//            return super.shouldInterceptRequest(view, request);
        }

        // API21以下用shouldInterceptRequest(WebView view, String url)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }
    };

    private void getRespone (final String urlConection) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("TTG", "shouldOverrideUrlLoading: start");
                    URL url = new URL(urlConection);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    //获得结果码
                    int responseCode = connection.getResponseCode();
                    Log.d("TTG", "shouldOverrideUrlLoading responseMessage: " + getResponseHeader(connection));
                    if(responseCode == 200){
                        //请求成功 获得返回的流
                        InputStream is = connection.getInputStream();
                        String s = convertToString(is);
                        Log.d("TTG", "shouldOverrideUrlLoading: " + s);
                    }else {
                        //请求失败
                    }
                } catch (MalformedURLException e) {
                    Log.d("TTG", "shouldOverrideUrlLoading MalformedURLException: " + e.getMessage());
                } catch (IOException e) {
                    Log.d("TTG", "shouldOverrideUrlLoading MalformedURLException: " + e.getMessage());
                }
            }
        }).start();
    }
    String resFileName = null;
    String isType = "";
    DVDUrlCache dvdUrlCache;
    private WebResourceResponse getCacheRe (String url) {

        String params = "";
        //读取当前webview正准备加载URL资源
        if (url.indexOf("?") != -1) {
            params = url.substring(url.lastIndexOf("?"), url.length());
            url = url.substring(0, url.lastIndexOf("?"));
        }
        try {
            //根据资源url获取一个你要缓存到本地的文件名，一般是URL的MD5
            resFileName = Util.getResourcesFileName(url);
            if (TextUtils.isEmpty(resFileName)) {
                return null;
            }
            if (url.lastIndexOf("html") != -1) {
                isType = "text/html";
            } else if (url.lastIndexOf("png") != -1) {
                isType = "image/png";
            } else if (url.lastIndexOf("js") != -1) {
                isType = "text/javascript";
            } else if (url.lastIndexOf("css") != -1) {
                isType = "text/css";
            } else if (url.lastIndexOf("ico") != -1) {
                isType = "image/ico";
            } else if (url.lastIndexOf("jpg") != -1) {
                isType = "image/jpg";
            } else {
                isType = "application/json";
            }
            if (Util.isNetworkOpen(this)) {
                url = url.concat(params);
                dvdUrlCache.register(url, resFileName, isType, "UTF-8", dvdUrlCache.ONE_DAY);
                dvdUrlCache.load(url);
                return null;
            }
            //url包含以下这些路径的时候不对其进行缓存
            if (url.indexOf("getRandKey") != -1//密码键盘
                    || url.indexOf("getMappingAarry") != -1//密码键盘
                    || url.indexOf("getByParentIdAndType") != -1//新建地址
                    || url.indexOf("getIsentReadCount") != -1
                    || url.indexOf("svg") != -1//密码键盘资源
                    || url.indexOf("getUserShoppingCar") != -1//购物车
                    || url.indexOf("getuserinfo") != -1//获取用户是否认证
                    || url.indexOf("getGoodSkuProp") != -1//过去商品详
                    || url.indexOf("newsManagement") != -1
                    || url.indexOf("getUserMessages") != -1
                    || url.indexOf("getAllMessageType") != -1
                    ) {
                return null;
            }


            //这里是处理本地缓存的URL，缓存到本地，或者已经缓存过的就直接返回而不去网络进行加载
            dvdUrlCache.register(url, resFileName,
                    isType, "UTF-8", dvdUrlCache.ONE_DAY);
            Log.i("whj", url + "====" + resFileName + "===" + isType);
            if (Util.isNetworkOpen(this)) {
                dvdUrlCache.load(url);
                return null;
            } else {
                return dvdUrlCache.load(url);
            }
        } catch (Exception e) {
        }
        return null;
    }

    //读取响应头
    private String getResponseHeader(HttpURLConnection conn) {
        Map<String, List<String>> responseHeaderMap = conn.getHeaderFields();
        int size = responseHeaderMap.size();
        StringBuilder sbResponseHeader = new StringBuilder();
        for(int i = 0; i < size; i++){
            String responseHeaderKey = conn.getHeaderFieldKey(i);
            String responseHeaderValue = conn.getHeaderField(i);
            sbResponseHeader.append(responseHeaderKey);
            sbResponseHeader.append(":");
            sbResponseHeader.append(responseHeaderValue);
            sbResponseHeader.append("\n");
        }
        return sbResponseHeader.toString();
    }


    WebChromeClient webChromeClient = new WebChromeClient() {
//        onReachedMaxAppCacheSize
    };

    public String convertToString(InputStream inputStream){
        StringBuffer string = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                string.append(line + "\n");
            }
        } catch (IOException e) {}
        return string.toString();
    }


    /**
     * webView的设置
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void setmWebView(WebView mWebView) {
        WebSettings webSettings = mWebView.getSettings();
        // 设置WebView是否允许执行JavaScript脚本，默认false，不允许。
        webSettings.setJavaScriptEnabled(true);
        // DOM存储API是否可用, 默认fasle
        webSettings.setDomStorageEnabled(true);
        // 应用缓存API是否可用，默认值false, 结合setAppCachePath(String)使用。
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String appCacheDir = this.getDir("cache", Context.MODE_PRIVATE).getPath();
        // 设置应用缓存文件的路径。为了让应用缓存API可用，此方法必须传入一个应用可写的路径。该方法只会执行一次，重复调用会被忽略。
        webSettings.setAppCachePath(appCacheDir);
        //设置数据库缓存路径
        webSettings.setDatabasePath(appCacheDir);
        // 设置缓冲大小，8M
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        if (Util.isNetworkOpen(this)) {
            // 根据cache-control决定是否从网络上取数据。
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //没网，则从本地获取，即离线加载
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // WebView先不要自动加载图片，等页面finish后再发起图片加载
        if(Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= 12) {
            webSettings.setAllowContentAccess(true);
        }
        // WebView是否支持HTML的“viewport”标签或者使用wideviewport。
        // 设置值为true时，布局的宽度总是与WebView控件上的设备无关像素（device-dependentpixels）宽度一致。
        // 当值为true且页面包含viewport标记，将使用标签指定的宽度。如果页面不包含标签或者标签没有提供宽度，那就使用wide viewport。
        webSettings.setUseWideViewPort(true);
    }
}
