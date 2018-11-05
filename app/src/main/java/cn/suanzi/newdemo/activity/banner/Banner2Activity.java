package cn.suanzi.newdemo.activity.banner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.adapter.GlideImageLoader;
import cn.suanzi.newdemo.pojo.BannerPojo;

public class Banner2Activity extends AppCompatActivity implements OnBannerListener {
    Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<BannerPojo> images = new ArrayList<>();
        BannerPojo bannerPojo1 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/1508902016497c.jpg", "1");
        BannerPojo bannerPojo2 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/1508902103f6ab.jpg", "2");
        BannerPojo bannerPojo3 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/15089021634373.jpg", "3");
        images.add(bannerPojo1);
        images.add(bannerPojo2);
        images.add(bannerPojo3);
        setContentView(R.layout.activity_banner2);
        banner = (Banner) findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        // 设置轮播时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(this);
        Log.d("test", "testbanner: 11111111111111");
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startAutoPlay();
        Toast.makeText(this, "onResume startAutoPlay", Toast.LENGTH_LONG).show();
        Log.d("TEST", "testbanner onResume: startAutoPlay");
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopAutoPlay();
        Toast.makeText(this, "onPause stopAutoPlay", Toast.LENGTH_LONG).show();
        Log.d("TEST", "testbanner onPause: stopAutoPlay");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnBannerClick(int position) {
        startActivity(new Intent(this, BannerActivity.class));
    }
}
