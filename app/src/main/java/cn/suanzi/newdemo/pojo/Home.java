package cn.suanzi.newdemo.pojo;

/**
 * Created by liyanfang on 2017/11/28.
 */
public class Home<T> {
    /** 描述*/
    private String name;
    /** id*/
    private int id;

    private String text;

    private Boolean isFinish;

    private Class<T> tClass;

    public Home(String name, int id, Class<T> tClass) {
        this.name = name;
        this.id = id;
        this.tClass = tClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public Boolean getFinish() {
        return isFinish;
    }

    public Class<T> gettClass() {
        return tClass;
    }

    public void settClass(Class<T> tClass) {
        this.tClass = tClass;
    }
}
