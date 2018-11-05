package cn.suanzi.newdemo.adapter.recycle;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.adapter.recyclebase.BaseAdapter;
import cn.suanzi.newdemo.adapter.recyclebase.BaseVH;

/**
 * Created by liyanfang on 2018/8/7.
 */

public class RecycleAdapter extends BaseAdapter<String> {

    public RecycleAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_recyclerview;
    }

    @Override
    protected void renderCommonView(BaseVH holder, int position) {
        String item = datas.get(position);
        Log.d(TAG, "renderCommonView: " + item);
        TextView tvItem = holder.getView(R.id.tv_rcycle_item);
        if (tvItem != null) {
            tvItem.setText(item + " - " + position);
        }

    }
}
