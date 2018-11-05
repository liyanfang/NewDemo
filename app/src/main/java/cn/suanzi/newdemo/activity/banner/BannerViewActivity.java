package cn.suanzi.newdemo.activity.banner;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.YxBanner;
import cn.suanzi.newdemo.call.YxBannerCallBack;
import cn.suanzi.newdemo.pojo.BannerPojo;

/**
 * 轮播图
 */
public class BannerViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = BannerViewActivity.class.getSimpleName();
    ViewStub viewStub;
    RecyclerView recyclerView;
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_banner);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        setBanner();
        setListView();
        setappbar();
    }

    private void setappbar () {
        LayoutTransition mTransition = new LayoutTransition();
        /**
         * 添加View时过渡动画效果
         */
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "translationY", 0, 1.f).
                setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        mTransition.setAnimator(LayoutTransition.APPEARING, addAnimator);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setLayoutTransition(mTransition);

        final int headerHeight = getResources().getDimensionPixelOffset(R.dimen.header_height);
        final int minHeight = getResources().getDimensionPixelOffset(R.dimen.min_height);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);
                if (verticalOffset > (headerHeight - minHeight)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTvTitle.setVisibility(View.VISIBLE);
                            Log.d("TTG", "onOffsetChanged: setVisibilitysetVisibility");
//                            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) headerLayout.getLayoutParams();
//                            mParams.setScrollFlags(0);
//                            headerLayout.setLayoutParams(mParams);
//                            headerLayout.setVisibility(View.GONE);
                        }
                    },100);
                } else {
                    mTvTitle.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setBanner () {
        YxBanner yxBanner = (YxBanner) findViewById(R.id.yxbanner);
        List<BannerPojo> bannerList = new ArrayList<>();
        BannerPojo bannerPojo1 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/1508902016497c.jpg", "1");
        BannerPojo bannerPojo2 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/1508902103f6ab.jpg", "2");
        BannerPojo bannerPojo3 = new BannerPojo("https://cdnbdimg.tatagou.com.cn/15089021634373.jpg", "3");
        bannerList.add(bannerPojo1);
        bannerList.add(bannerPojo2);
        bannerList.add(bannerPojo3);
        assert yxBanner != null;
        yxBanner.setIndicatorGravity(Gravity.CENTER).setYxCallBack(yxCallBack).show(bannerList);
    }

    private List<String> getData () {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("第" + i + "条数据……");
        }
        return list;
    }

    private void setListView () {
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.listview);
        CommonAdapter commonAdapter = new CommonAdapter(getData());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(commonAdapter);
    }

    private void setViewStud (String text) {
        View view = findViewById(R.id.ly_top_view);
        if (viewStub == null) {
            viewStub = (ViewStub) findViewById(R.id.start_stub);
            View inflate = viewStub.inflate(); // 只能执行一次
            assert view != null;
            view.invalidate();
        }
        TextView tv = (TextView) findViewById(R.id.tv_stub);
        assert tv != null;
        tv.setText(text);
    }

    YxBannerCallBack yxCallBack = new YxBannerCallBack() {
        @Override
        public void onBannerItemClick(int position) {
            super.onBannerItemClick(position);
            Toast.makeText(BannerViewActivity.this, "点击了》》》" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public ImageView displayImage(Context context, Object path, ImageView imageView) {
            if (path instanceof BannerPojo) {
                Glide.with(BannerViewActivity.this).load(((BannerPojo) path).getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).fitCenter()
                        .crossFade().into(imageView);
            }
            return imageView;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_stub:
                setViewStud("已经进去程序，show Time");
                viewStub.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_stub:
                setViewStud("布局内部点击了操作哟");
                viewStub.setVisibility(View.GONE);
                break;

        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public View getItemView () {
            return itemView;
        }

    }

    public  class CommonAdapter extends RecyclerView.Adapter<BannerViewActivity.RecyclerViewHolder> {

        private List<String> mData = new ArrayList<>();

        public CommonAdapter(List<String> mData) {
            this.mData = mData;
        }

        @Override
        public BannerViewActivity.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(BannerViewActivity.this).inflate(R.layout.item_view, parent,false);
            return new BannerViewActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BannerViewActivity.RecyclerViewHolder holder, int position) {
//            holder.updatePosition(position);
            TextView textView = (TextView) holder.getItemView().findViewById(R.id.tv_toast);
            textView.setText(mData.get(position));
//            convert(holder, mData.get(position));
        }

//        public abstract void convert(ViewHolder holder, String text);

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

}
