package cn.suanzi.newdemo.pojo;

import java.util.List;

/**
 * 频道
 * Created by liyanfang on 2017/6/15.
 */
public class Channel extends CommonResponseResult{
    /**频道专题标题*/
    private String channelName;
    /**频道专题id*/
    private int channelId;
    /** 专场*/
    private List<Special> normalSpecialList;
    /** 当前服务器的时间*/
    private String timestamp;
    /** 本地时间戳*/
    private long currentTimeMillis;

    public Channel() {
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public List<Special> getNormalSpecialList() {
        return normalSpecialList;
    }

    public void setNormalSpecialList(List<Special> normalSpecialList) {
        this.normalSpecialList = normalSpecialList;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }
}
