package cn.suanzi.newdemo.adapter.recyclebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyanfang on 2018/7/6.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseVH> {
    protected final String TAG = this.getClass().getSimpleName();
    private LayoutInflater inflater;
    protected Context context;
    protected List<T> datas;
    private onItemClickListener itemClickListener;

    public BaseAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseVH(inflater.inflate(getLayoutId(), parent, false));
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(BaseVH holder, final int position) {
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null)
                itemClickListener.onItemClick(datas.get(position), position);
        });
        renderCommonView(holder, position);
    }

    protected abstract void renderCommonView(BaseVH holder, int position);

    public T get(int position){
        return datas.get(position);
    }

    public List<T> getDatas () {
        return datas;
    }

    /**
     * 添加新数据
     * @param list
     */
    public void addDatas (List<T> list) {
        if (list == null) {
            return;
        }
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加新数据
     * @param list
     */
    public void setDatas (List<T> list) {
        if (list == null) {
            return;
        }
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (datas == null)
            return 0;
        return datas.size();
    }

    public void setOnItemClickListener(onItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface onItemClickListener<T>{
        void onItemClick(T t, int position);
    }

}
