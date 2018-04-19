/**    
 * @author maomy  
 * @Description: 自定义 等待框
 * @Package com.ciapc.tzd.modules.common.dialog   
 * @Title: DefineProgressDialog.java   
 * @date 2015年1月6日 下午6:36:23   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package cn.suanzi.newdemo.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.suanzi.newdemo.R;


public class JZProgressDialog extends ProgressDialog implements Runnable {
	private Context mContext;
	private ImageView mIvIamgeView;
	private AnimationDrawable mAnimaition;

	public JZProgressDialog(Context context) {
		super(context);
		this.mContext = context;
	}



	public JZProgressDialog(Context context, String str) {
		super(context, R.style.load_dialog);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(initView(mContext));
		mAnimaition = (AnimationDrawable) mIvIamgeView.getBackground();
		mAnimaition.setOneShot(false);
	}

	@Override
	public void show() {
		super.show();
		mAnimaition.start();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (mAnimaition.isRunning()) {
			mAnimaition.stop();
		}
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	private RelativeLayout initView(Context context) {
		RelativeLayout relativeLayout = new RelativeLayout(mContext);
		relativeLayout.setGravity(RelativeLayout.CENTER_IN_PARENT);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mIvIamgeView = new ImageView(context);
		RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mIvIamgeView.setBackgroundResource(R.drawable.animation);
		relativeLayout.addView(mIvIamgeView, layoutParams4);
		return relativeLayout;
	}

	@Override
	public void run() {

	}
}
