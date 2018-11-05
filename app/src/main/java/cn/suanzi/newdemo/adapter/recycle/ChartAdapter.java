package cn.suanzi.newdemo.adapter.recycle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2018/9/10.
 */

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.BaseVH> {

    private LayoutInflater inflater;
    private Context context;
    private List<View> datas;

    public ChartAdapter(Context context, List<View> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }

    public void addDatas (List<View> views){
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.addAll(views);
        notifyDataSetChanged();
    }

    public void addItem (View view){
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.add(view);
        notifyDataSetChanged();
    }

    public int getSize () {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseVH(datas.get(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    public static class BaseVH extends RecyclerView.ViewHolder {

        private SparseArray<View> views = new SparseArray<>();
        public BaseVH(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(Integer resId) {
            if (null == views.get(resId)) {
                views.put(resId, itemView.findViewById(resId));
            }
            return (T) views.get(resId);
        }
    }
}
