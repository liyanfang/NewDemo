package cn.suanzi.newdemo.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  api
 * Created by liyanfang on 2016/5/31.
 */
public interface ApiService {

    @GET("getChannelSpecials")
    Call<ResponseBody> getChannelSpecials(@Query("channelId") String channelId);
}
