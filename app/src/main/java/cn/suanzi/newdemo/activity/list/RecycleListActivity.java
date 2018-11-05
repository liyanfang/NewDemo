package cn.suanzi.newdemo.activity.list;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.adapter.recycle.RecycleAdapter;
import cn.suanzi.newdemo.adapter.recyclebase.listener.EndlessRecyclerOnScrollListener;
import cn.suanzi.newdemo.adapter.recyclebase.wrapper.LoadMoreWrapper;

/**
 * Created by liyanfang on 2018/8/7.
 * recycle 上拉加载
 */

public class RecycleListActivity extends Activity {

    private RecyclerView recyclerView;

    private SmartRefreshLayout refreshLayout;

    private LoadMoreWrapper mLoadMoreWrapper;

    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.refreshLayout);
        initData();
        initRefreshLayout();
    }

    /**
     * 初始化数据加载
     */
    private void initRefreshLayout() {
        ClassicsHeader classicsHeader = new ClassicsHeader(this);
        classicsHeader.setTextSizeTitle(15);
        classicsHeader.setTextSizeTime(12);
        classicsHeader.setDrawableArrowSize(15);
        refreshLayout.setRefreshHeader(classicsHeader);
        ClassicsFooter classicsFooter = new ClassicsFooter(this);
        classicsFooter.setDrawableArrowSize(15);
        refreshLayout.setRefreshHeader(classicsHeader);
        refreshLayout.setRefreshFooter(classicsFooter);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setNoMoreData(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // 下拉刷新，刷新最新的数据
//            getInitInviteeList(false);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
//            getPresenter().getInviteeList(mCurrentDate, mPage, false);
        });
    }

    private void initData () {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getData();
        RecycleAdapter recycleAdapter = new RecycleAdapter(this, mList);
        mLoadMoreWrapper = new LoadMoreWrapper(recycleAdapter);
        recyclerView.setAdapter(mLoadMoreWrapper);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                mLoadMoreWrapper.setLoadState(mLoadMoreWrapper.LOADING);
                if (mList.size() < 100) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getData();
                            mLoadMoreWrapper.setLoadState(mLoadMoreWrapper.LOADING_COMPLETE);
                        }
                    }, 1500);

                } else {
                    mLoadMoreWrapper.setLoadState(mLoadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    private void getData() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            mList.add(String.valueOf(letter));
            letter++;
        }
    }

}
