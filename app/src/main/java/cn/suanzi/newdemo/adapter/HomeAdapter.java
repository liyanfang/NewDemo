package cn.suanzi.newdemo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.pojo.Home;

/**
 * Created by liyanfang on 2017/11/28.
 */
public class HomeAdapter extends BaseRecyclerAdapter<Home>{

    private WeakReference<Activity> mActivityWR;
    public void setDatas (List<Home> datats) {
       this.mDatas = datats;
        notifyDataSetChanged();
    }

    public HomeAdapter(Activity activity, List<Home> datats){
        this.mActivityWR = new WeakReference<Activity>(activity);
        mDatas = datats;
    }

    class ViewHolder extends Holder {
        TextView mTxt;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxt = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    @Override
    public int getItemCount(){
        return mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, final Home data) {
        if(viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).mTxt.setText(data.getName());
            ((ViewHolder) viewHolder).mTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivityWR.get().startActivity(new Intent(mActivityWR.get(), data.gettClass()));
                }
            });
        }
    }
}
