package cn.suanzi.newdemo.adapter;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.suanzi.newdemo.call.YxBannerCallBack;

/**
 * 首页滚屏图片
 * @author yanfang.li
 */
public class BannerAdapter extends PagerAdapter {
	private static final String TAG = BannerAdapter.class.getSimpleName();
	/** 图片的集合*/
	private List<ImageView> mImageViewList;
	/** 滑动的位置*/
	private double mRawX = 0;
	/** 回调*/
	private YxBannerCallBack mCallBack;

	/**
	 * 设置回调方法
	 * @param callBack 回调
     */
	public void setCallBack (YxBannerCallBack callBack) {
		this.mCallBack = callBack;
	}

	/**
	 * 有参构造方法
	 * @param images 图片集合
     */
	public BannerAdapter(List<ImageView> images) {
		this.mImageViewList = images;
	}

	@Override
	public int getCount() {
		if (mImageViewList != null) {
			// 位了循环滑动，所以getCount会返回一个超大值
			return mImageViewList.size() > 1 ? Integer.MAX_VALUE : mImageViewList.size();
		} else {
			return 0;
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View view = null;
		try {
			int index = position % mImageViewList.size();
			view = mImageViewList.get(index);
			ViewGroup group = (ViewGroup) view.getParent();
			if (group != null) {
				group.removeView(view);
			}
			container.addView(view);
		} catch (Exception e) {
			Log.d(TAG," instantiateItem srollAdapter 滚屏图片.",e);
		}

		// 触摸事件
		if (view != null) {
			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN://按下
							mRawX = event.getRawX();
							if (mCallBack != null) {
								mCallBack.onScrollTouch(false);
							}
							break;
						case MotionEvent.ACTION_UP://离开
							double distanceUpX = event.getRawX() - mRawX;
							if (distanceUpX <= 11 && mImageViewList != null && mCallBack != null) {
								int index = position % mImageViewList.size();
								mCallBack. onBannerItemClick(position % mImageViewList.size());
							}
							if (mCallBack != null) {
								mCallBack.onScrollTouch(true);
							}
							break;
						case MotionEvent.ACTION_CANCEL://消息丢失和取消的
							if (mCallBack != null) {
								mCallBack.onScrollTouch(true);
							}
							break;
						default:
							break;
					}
					return true;
				}
			});
		}
		return view;
	}

//	private void onItemClick (int position) {
//		if (mSpecialList != null ) {
//			// 当mSpecialList集合小于2的时候，为了使两张图能循环滑动，并且不出现空白界面，适配器的集合mSpecialList集合的长度，故取position的余数
//			position = (mSpecialList.size() <= 2) ? position % 2 : position;
////			CommSpAdapterManage.onSpClickListener(mActivity, mSpecialList.get(position), mChannelId, Const.Event.HOME);
//		}
//	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if (observer != null) {
			super.unregisterDataSetObserver(observer);
		}
	}
}
