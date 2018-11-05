package cn.suanzi.newdemo.activity.list;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.adapter.ListAdapter;

/**
 * Created by liyanfang on 2017/11/27.
 */
public class RecyclerViewActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerList;
    private GalleryAdapter mAdapter;
    private ListAdapter mListAdapter;
    private List<String> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recycleview);
        initDatas();
        //得到控件
        mRecyclerList = (RecyclerView) findViewById(R.id.id_recyclerview_list);
        //设置布局管理器
        setRecyclerManager(mRecyclerList, LinearLayoutManager.VERTICAL);

        mListAdapter = new ListAdapter(this, getList("0"));
        mRecyclerList.setAdapter(mListAdapter);
        //设置适配器(横向类目)
        setFooterView(mRecyclerList);
        setHeader(mRecyclerView);
        setRecyclerViewManager(mRecyclerView, LinearLayoutManager.HORIZONTAL);
        mAdapter = new GalleryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(new Callback(){
            @Override
            public void onClick(int position) {
                super.onClick(position);
                mRecyclerView.scrollBy(500,0);
                mListAdapter.setDatas(getList(String.valueOf(position)));
            }
        });
        mRecyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.top_recycleview, view, false);
        mRecyclerView = (RecyclerView) header.findViewById(R.id.id_recyclerview_horizontal);
        mListAdapter.setHeaderView(header);
    }

    private void setFooterView(RecyclerView view) {
        View footerView = LayoutInflater.from(this).inflate(R.layout.ttg_load_more_auto_bottom, null, false);
        mListAdapter.setFooterView(footerView);
    }

    private void setRecyclerViewManager (RecyclerView recyclerView, int  orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(orientation);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    private void setRecyclerManager (RecyclerView recyclerView, int  orientation) {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        linearLayoutManager.setOrientation(orientation);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initDatas(){
        mDatas = new ArrayList<>(Arrays.asList("精选", "男装", "女装", "母婴", "饰品", "裤子", "衣服", "居家日用", "鞋子"));
    }

    private List<String> getList(String index){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("第一条测试数据：", "第二条测试数据：", "测试数据……", "测试数据", "测试数据……", "测试数据……33333", "你好", "测试数据……", "测试数据……222", "测试数据%%%%%%%%%%", "测试数据……", "测试数据……", "你好", "测试数据……", "测试数据……", "测试数据%%%%%%%%%%", "测试数据……", "测试数据……"));
        List<String> newlist = new ArrayList<>();
        for (String str : strings) {
            newlist.add(str.concat(index));
        }
        return newlist;
    }

    public class Callback {
        public void onClick(int position){};
    }

    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{
        private LayoutInflater mInflater;
        private List<String> mDatas;

        public GalleryAdapter(Context context, List<String> datats){
            mInflater = LayoutInflater.from(context);
            mDatas = datats;
        }

        public void setCallback (Callback callback) {
            this.callback = callback;
        }

        private Callback callback;


        public class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(View arg0){
                super(arg0);
            }
            ImageView mImg;
            TextView mTxt;
        }

        @Override
        public int getItemCount(){
            return mDatas.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i){
            View view = mInflater.inflate(R.layout.item_recycleview, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.mTxt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);

            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i){
            viewHolder.mTxt.setText(mDatas.get(i));
            viewHolder.mTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TTG", "onClick: " + i);
                    if (callback != null) {
                        callback.onClick(i);
                    }
                }
            });
        }
    }

}
