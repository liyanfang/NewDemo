package cn.suanzi.newdemo.pojo;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首页小模块
 * Created by liyanfang on 2016/7/4.
 */
public class Special implements Parcelable {
    /** 封面图片URL*/
    private String coverImg;
    /** 编码*/
    private String id;
    /** 下线时间*/
    private String offlineTime;
    /** 内部识别标题*/
    private String title;
    /** NORMAL: 一般卖场；BANNER: Banner专题；CATEGORY: 分类专题。*/
    private String type;
    /** BANNER 是否是BANNER模式*/
    private String isBanner;
    /** 商品*/
    private Item item;
    /** 是否关闭专场倒计时*/
    private String isCountDown;
    /** 跳转的URl*/
    private String url;
    /** 专场角标*/
    private String subImage;
    /** 图片的宽高比*/
    private double coverImgScale;
    /** 日志上报的额外参数*/
    private String marker;
    /** ttgUrl*/
    private String ttgUrl;
    /** 专场内部的商品数目*/
    private String itemQty;
    /** 宽高比*/
    private String aspectRadio;

    public String getTtgUrl() {
        return ttgUrl;
    }

    public void setTtgUrl(String ttgUrl) {
        this.ttgUrl = ttgUrl;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public double getCoverImgScale() {
        return coverImgScale;
    }

    public void setCoverImgScale(double coverImgScale) {
        this.coverImgScale = coverImgScale;
    }

    public String getSubImage() {
        return subImage;
    }

    public void setSubImage(String subImage) {
        this.subImage = subImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public Special() {
    }


    public Special(String id) {
        this.id = id;
    }

    protected Special(Parcel in) {
        coverImg = in.readString();
        id = in.readString();
        offlineTime = in.readString();
        title = in.readString();
        type = in.readString();
        isBanner = in.readString();
        itemQty =in.readString();
        aspectRadio =in.readString();
    }

    public static final Creator<Special> CREATOR = new Creator<Special>() {
        @Override
        public Special createFromParcel(Parcel in) {
            return new Special(in);
        }

        @Override
        public Special[] newArray(int size) {
            return new Special[size];
        }
    };

    public String getIsCountDown() {
        return isCountDown;
    }
    public void setIsCountDown(String isCountDown) {
        this.isCountDown = isCountDown;
    }

    public String getIsBanner() {
        return isBanner;
    }

    public void setIsBanner(String isBanner) {
        this.isBanner = isBanner;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAspectRadio() {
        return aspectRadio;
    }

    public void setAspectRadio(String aspectRadio) {
        this.aspectRadio = aspectRadio;
    }


    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coverImg);
        dest.writeString(id);
        dest.writeString(offlineTime);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(isBanner);
        dest.writeString(itemQty);
        dest.writeString(aspectRadio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Special special = (Special) o;
        return id.equals(special.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
