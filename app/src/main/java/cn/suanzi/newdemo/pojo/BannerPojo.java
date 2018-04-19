package cn.suanzi.newdemo.pojo;

/**
 * banner pojo类
 * Created by liyanfang on 2018/4/9.
 */
public class BannerPojo {
    private String url;

    private String index;

    public BannerPojo(String url, String index) {
        this.url = url;
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
