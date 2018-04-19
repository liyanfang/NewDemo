package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by liyanfang on 2017/7/10.
 */
public class CoordinatorLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rc_home);
        List<String> list = initData();
        CommonAdapter commonAdapter = new CommonAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(commonAdapter);
    }

    private List<String> initData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            data.add("第" + i + "条测试数据balabalabalabala……");
        }
        return data;
    }

    class RecyclerViewHolder extends ViewHolder {

        private View itemView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public View getItemView () {
            return itemView;
        }

    }

    public  class CommonAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private List<String> mData = new ArrayList<>();

        public CommonAdapter(List<String> mData) {
            this.mData = mData;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CoordinatorLayoutActivity.this).inflate(R.layout.item_view, parent,false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
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
