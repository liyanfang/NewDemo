package cn.suanzi.newdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.TypeReference;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.Callback;
import cn.suanzi.newdemo.View.ScrollGridLayoutManager;
import cn.suanzi.newdemo.adapter.MainRvAdapter;
import cn.suanzi.newdemo.adapter.base.BaseQuickAdapter;
import cn.suanzi.newdemo.api.ApiService;
import cn.suanzi.newdemo.api.RetrofitUtil;
import cn.suanzi.newdemo.pojo.Channel;
import cn.suanzi.newdemo.pojo.CommPojo;
import cn.suanzi.newdemo.pojo.Special;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by liyanfang on 2016/7/16.
 */
public class RecycleScrollFragment extends BaseFragment {
    RecyclerView rvNewsList;

    SwipeRefreshLayout swipeRefresh;
    private int type;

    private boolean mIsMulti = false;
    private String id;
    private View myview;
    private List<String> myList=new ArrayList<>();
    private Context mContext;

    private Callback mCallback;

    private MainRvAdapter mainRvAdapter;

    public void setCallback (Callback callback) {
        this.mCallback = callback;
    }
    public static RecycleScrollFragment newInstance(String id) {
        RecycleScrollFragment fragment = new RecycleScrollFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CHANNEL_ID", id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        myview = inflater.inflate(R.layout.recycle_scroll_fragmetn,null);
        rvNewsList= (RecyclerView)myview.findViewById(R.id.rv_news_list);
        initView();
        initDatas();
        // 防止空白页
        ViewGroup parent = (ViewGroup) myview.getParent();
        if (parent != null) {
            parent.removeView(myview);
        }
        return myview;
    }

    public void initDatas () {
        String mChannelId = getArguments().getString("CHANNEL_ID");
        requestApi(mChannelId);
    }

    /**
     * 下拉
     */
    public void onPullDown () {
        Log.d("TTG", "onRefresh: onPullDown **************" );
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Random ran = new Random();
                onPullData(ran.nextInt(20));
                if (mCallback != null) {
                    mCallback.onPullDown(true);
                }
            }
        }, 2000);
    }

    private void onPullData (int data) {
        List<String> datas = new ArrayList<>();
        for (int i = 20; i > 0 ; i--) {
            datas.add(0,  data + "-" + i);
        }
        mainRvAdapter.replaceData(datas);
    }

    private void onLoadMore (int data) {
        List<String> datas = new ArrayList<>();
        for (int i = 20; i > 0 ; i--) {
            datas.add(0,  data + "*" + i);
        }
        mainRvAdapter.addData(datas);
    }

    public void onAutoLoadMore () {
        Log.d("TTG", "onRefresh: onPullDown **************" );
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Random ran = new Random();
                onLoadMore(ran.nextInt(20));
                if (mCallback != null) {
                    mCallback.onLoadMore(true);
                }
            }
        }, 2000);
    }

    private void initView(){
        ScrollGridLayoutManager scrollGridLayoutManager=new ScrollGridLayoutManager(mContext,3);
        scrollGridLayoutManager.setScrollEnabled(false);
        rvNewsList.setLayoutManager(scrollGridLayoutManager);
        mainRvAdapter = new MainRvAdapter(myList);
        rvNewsList.setAdapter(mainRvAdapter);
        View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.ttg_load_more_auto_bottom, (ViewGroup) getView(), false);
        mainRvAdapter.setFooterView(footerView);
        mainRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext,"下面点击的是："+position,Toast.LENGTH_SHORT);
            }
        });
    }

    private void requestApi (String id) {
        RetrofitUtil.getInstance().getService(ApiService.class).getChannelSpecials(id).enqueue(new retrofit2.Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                CommPojo<Channel> channle = onParseBodyData(response.body(), new TypeReference<CommPojo<Channel>>() {
                }.getType());
                Log.d("TTG", "onResponse: " + JSON.toJSONString(channle));
                List<Special> normalSpecialList = channle.getData().getNormalSpecialList();
                List<String> list = new ArrayList<String>();
                for (Special special : normalSpecialList) {
                    list.add(special.getTitle());
                }
                if (mainRvAdapter == null) {
                    mainRvAdapter = new MainRvAdapter(list);
                    rvNewsList.setAdapter(mainRvAdapter);
                } else {
                    mainRvAdapter.addData(list);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    /**
     * 数据转换
     * @param body api返回值
     * @param type 转换类型
     * @param <T> 泛型
     * @return
     */
    public static <T> T onParseBodyData (ResponseBody body, Type type) {
        if (body != null) {
            try {
                Reader reader = body.charStream();
                JSONReader jsonReader = new JSONReader(reader);
                T object = jsonReader.readObject(type);
                jsonReader.close();
                reader.close();
                return object;
            } catch (Exception e) {
                Log.d("TTG", "onParseBodyData:" + e.getMessage(), e);
            }
        }
        return null;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getUserVisibleHint Fragment的UI是否是可见
        if (getUserVisibleHint() && myview != null && !mIsMulti) {//只有一个fragment
            mIsMulti = true;
        }
    }

    boolean isLoadMoreData = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//fragment是否可见操作
        if (isVisibleToUser && mCallback != null) {
            mCallback.onLoadMore(isLoadMoreData);
        }
        if (isVisibleToUser && isVisible() && myview != null && !mIsMulti) {
            mIsMulti = true;
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }
}
