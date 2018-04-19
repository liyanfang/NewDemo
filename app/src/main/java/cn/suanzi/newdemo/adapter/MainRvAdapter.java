package cn.suanzi.newdemo.adapter;

import android.util.Log;

import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.adapter.base.BaseQuickAdapter;
import cn.suanzi.newdemo.adapter.base.BaseViewHolder;


/**
 * Created by Riven on 2017/10/24.
 */

public class MainRvAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public MainRvAdapter(List data) {
        super( R.layout.rv_item, data);
    }


    public void addDatas (List data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Log.d(TAG, "MainRvAdapter convert: " + item);
        helper.setText(R.id.rv_item_tv,"这是第" + item + "本书");
    }


}
