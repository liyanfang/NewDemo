package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.View.MyListview;
import cn.suanzi.newdemo.View.MyScrollView;

/**
 * Created by liyanfang on 2018/5/17.
 * 描述：滚动测试
 */
public class ScrollConflictActivity extends Activity {

    private MyListview mListView;

    private boolean isScrollToBottom = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scroll_conflict);
        mListView = (MyListview) findViewById(R.id.lv_list);
        final MyScrollView scrollView = (MyScrollView) findViewById(R.id.scrollView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getList());
        mListView.setAdapter(adapter);
        mListView.setParent(scrollView);
        scrollView.setScrollToBottomListener(new MyScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollToBottom() {
                isScrollToBottom = true;
                mListView.setScrollToBottom(true);
            }

            @Override
            public void onNotScrollToBottom() {
                isScrollToBottom = false;
                mListView.setScrollToBottom(false);
            }
        });


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = mListView.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        Log.d("ListView", "##### 滚动到顶部 #####");
                        mListView.setPosition(true);
                    } else {
                        mListView.setPosition(false);
                    }
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = mListView.getChildAt(mListView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == mListView.getHeight()) {
                        Log.d("ListView", "##### 滚动到底部 ######");
                        mListView.setPosition(false);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //do nothing
            }

        });
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    private List<String> getList () {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("我是第" + i + "条测试数据");
        }
        return list;
    }
}
