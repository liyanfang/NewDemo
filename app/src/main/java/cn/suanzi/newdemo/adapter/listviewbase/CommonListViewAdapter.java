// ---------------------------------------------------------
// @author    
// @version   1.0.0
// @createTime 2015.5.14
// @copyright 版权所有 (c) 2015 杭州算子科技有限公司 保留所有版权
// ---------------------------------------------------------
package cn.suanzi.newdemo.adapter.listviewbase;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 继承BaseAdapter的抽象类，子类只需重写抽象方法就可以建立适配器
 * @author 
 * @param <T>//未指定的泛型T
 */
public abstract class CommonListViewAdapter<T> extends BaseAdapter {
	/** 上下文*/
	protected Activity mActivity;
	/** 集合*/
	protected List<T> mDataList;


	/**
	 * 构造方法
	 * @param activity 上下文
	 * @param dataList 数据集合
     */
	public CommonListViewAdapter(Activity activity, List<T> dataList) {
		super();
		this.mActivity = activity;
		this.mDataList = dataList;
	}

	public void addItems(List<T> dataList){
		if(dataList == null){
			return ;
		}
		if(mDataList == null){
			mDataList = dataList;
		}else{
			mDataList.addAll(dataList);
		}
		notifyDataSetChanged();
	}

	public void addItems(List<T> dataList, int position){
		if(dataList == null){
			return ;
		}
		if(mDataList == null){
			mDataList = dataList;
		} else if (position == 0){
			mDataList.addAll(position, dataList);
		} else {
			mDataList.addAll(dataList);
		}
		notifyDataSetChanged();
	}

	public void setItems(List<T> dataList){
		mDataList = dataList;
		this.notifyDataSetChanged();
	}
	public List<T> getItems (){
		return mDataList;
	}

	@Override
	public int getCount() {
		if(mDataList == null) {
			return 0;
		}
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		if(mDataList ==null) {
			return null;
		}
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if (observer != null) {
			super.unregisterDataSetObserver(observer);
		}
	}
}
