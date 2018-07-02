package cn.suanzi.newdemo.pojo.video;

/**
 * Created by liyanfang on 2018/6/19.
 * 描述：
 */
public class VideoItem {
    private int id;
    private String displayName;
    private String path;
    private long size;
    private long modified;
    private long duration;
    private boolean checked;

    public VideoItem(int id, String displayName, String path, long size, long modified, long duration) {
        this.duration = duration;
        this.id = id;
        this.displayName = displayName;
        this.path = path;
        this.size = size;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName == null ? "" : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
