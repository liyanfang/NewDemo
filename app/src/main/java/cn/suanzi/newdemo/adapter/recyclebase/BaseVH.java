package cn.suanzi.newdemo.adapter.recyclebase;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by liyanfang on 2018/7/6.
 */

public class BaseVH extends RecyclerView.ViewHolder {
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
