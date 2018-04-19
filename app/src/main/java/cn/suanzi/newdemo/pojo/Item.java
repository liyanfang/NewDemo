package cn.suanzi.newdemo.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 专场列表
 * Created by liyanfang on 2016/7/8.
 */
public class Item extends CommonResponseResult implements Serializable {
    /** 商品id*/
    private String id;
    /** 商品ID*/
    private String taobaoId;
    /** 包邮*/
    private String badges;
    /** 图片URL*/
    private String coverImg;
    /** 现在价格*/
    private String finalPrice;
    /** 是否上新*/
    private String isTodayOnline;
    /** 原价*/
    private String oriPrice;
    /** 淘宝*/
    private String taobaoType;
    /** 标题*/
    private String title;
    /** 数据列表*/
    private List<Item> items;
    /** 时间*/
    private Special special;
    /** 销售数量*/
    private String sellCount;
    /** 商品的一级类目*/
    private String gCat;
    /** 商品的二级类目*/
    private String gSubCat;
    /** 日志上报的额外参数*/
    private String marker;
    /**劵后价  专享价 拍立减*/
    private String label;
    /** >= 3.3.0.0 支持统跳*/
    private String ttgUrl;

    public Item() {
    }

    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }



    public String getgSubCat() {
        return gSubCat;
    }

    public void setgSubCat(String gSubCat) {
        this.gSubCat = gSubCat;
    }

    public String getgCat() {
        return gCat;
    }

    public void setgCat(String gCat) {
        this.gCat = gCat;
    }

    public String getSellCount() {
        return sellCount;
    }

    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    public Special getSpecial() {
        return special;
    }

    public void setSpecial(Special special) {
        this.special = special;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaobaoId() {
        return taobaoId;
    }

    public void setTaobaoId(String taobaoId) {
        this.taobaoId = taobaoId;
    }

    public String getBadges() {
        return badges;
    }

    public void setBadges(String badges) {
        this.badges = badges;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getIsTodayOnline() {
        return isTodayOnline;
    }

    public void setIsTodayOnline(String isTodayOnline) {
        this.isTodayOnline = isTodayOnline;
    }

    public String getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(String oriPrice) {
        this.oriPrice = oriPrice;
    }

    public String getTaobaoType() {
        return taobaoType;
    }

    public void setTaobaoType(String taobaoType) {
        this.taobaoType = taobaoType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTtgUrl() {
        return ttgUrl;
    }

    public void setTtgUrl(String ttgUrl) {
        this.ttgUrl = ttgUrl;
    }

}
