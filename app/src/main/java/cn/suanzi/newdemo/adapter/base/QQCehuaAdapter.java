package cn.suanzi.newdemo.adapter.base;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.StartActivity;

/**
 * Created by liyanfang on 2018/7/12.
 */

public class QQCehuaAdapter extends CommonAdapter<String> {

    public QQCehuaAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_qq_cehua, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        String data= getDatas().get(position);
        TextView textView = holder.getView(R.id.tv_1);
        textView.setText(data);
        textView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, StartActivity.class));
        });

    }
}
