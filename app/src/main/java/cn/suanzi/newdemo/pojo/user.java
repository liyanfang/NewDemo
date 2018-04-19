package cn.suanzi.newdemo.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liyanfang on 2017/9/21.
 */
public class user implements Parcelable {

    public double finalPrice;
    public int id;
    public String taobaoId;
    public String title;
    public int oriPrice;
    public String badges;
    public String taobaoType;
    public int sellCount;
    public String label;
    public boolean isTodayOnline;
    public int rewardPoint;
    public String marker;
    public int gCat;
    public int gSubCat;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.finalPrice);
        dest.writeInt(this.id);
        dest.writeString(this.taobaoId);
        dest.writeString(this.title);
        dest.writeInt(this.oriPrice);
        dest.writeString(this.badges);
        dest.writeString(this.taobaoType);
        dest.writeInt(this.sellCount);
        dest.writeString(this.label);
        dest.writeByte(this.isTodayOnline ? (byte) 1 : (byte) 0);
        dest.writeInt(this.rewardPoint);
        dest.writeString(this.marker);
        dest.writeInt(this.gCat);
        dest.writeInt(this.gSubCat);
    }

    public user() {
    }

    protected user(Parcel in) {
        this.finalPrice = in.readDouble();
        this.id = in.readInt();
        this.taobaoId = in.readString();
        this.title = in.readString();
        this.oriPrice = in.readInt();
        this.badges = in.readString();
        this.taobaoType = in.readString();
        this.sellCount = in.readInt();
        this.label = in.readString();
        this.isTodayOnline = in.readByte() != 0;
        this.rewardPoint = in.readInt();
        this.marker = in.readString();
        this.gCat = in.readInt();
        this.gSubCat = in.readInt();
    }

    public static final Parcelable.Creator<user> CREATOR = new Parcelable.Creator<user>() {
        @Override
        public user createFromParcel(Parcel source) {
            return new user(source);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };
}
