package cn.suanzi.newdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2017/11/28.
 */
public class ListAdapter extends BaseRecyclerAdapter<String>{

    public void setDatas (List<String> datats) {
       this.mDatas = datats;
        notifyDataSetChanged();
    }

    public ListAdapter(Context context, List<String> datats){
        mDatas = datats;
    }

    class ViewHolder extends BaseRecyclerAdapter.Holder {
        TextView mTxt;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxt = (TextView) itemView.findViewById(R.id.id_index_gallery_item_text);
        }
    }

    @Override
    public int getItemCount(){
        return mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_v_recycleview, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, String data) {
        if(viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).mTxt.setText(data);
        }
    }
}
