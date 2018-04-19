package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.View.AutoTextView;
import cn.suanzi.newdemo.View.HorizonScrollTextView;
import cn.suanzi.newdemo.View.HorizonScrollTextView2;

/**
 * 跑马灯
 */
public class AnimTextActivity extends Activity implements OnClickListener {

	private Button mBtnNext;
	private Button mBtnPrev;
	private AutoTextView mTextView02;
	final Handler handler = new Handler();
	// 自定义信息条数
	private static int sCount = 0;
	private List<String> str = new ArrayList<String>();
	private HorizonScrollTextView tv_2;
	private HorizonScrollTextView2 tv_3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_textview);
		init();
	}

	private void init() {
		//垂直滚动
		// 初始化数据
		str.add("信息1");
		str.add("信息2");
		str.add("信息3");
		sCount = str.size();
		mTextView02 = (AutoTextView) findViewById(R.id.switcher02);
		mTextView02.setText(str.get(0));
		//启动计时器
		handler.postDelayed(runnable, 5000);
		//handler.removeCallbacks(runnable);// 关闭定时器处理
		
		//水平滚动
		tv_2 = (HorizonScrollTextView)findViewById(R.id.tv_2);
		tv_2.setText("暂无任何预警信息!     暂无任何预警信息!2        暂无任何预警信息!3          暂无任何预警信息!4    "); 
		tv_2.setTextSize(20);  
		tv_2.setTextColor(Color.WHITE); 
		
		//水平滚动2
		tv_3= (HorizonScrollTextView2)findViewById(R.id.tv_3);
		tv_3.setText("金佛IE我就       佛i就困了睡         多久就分手快乐             大家束带结发");
		tv_3.init(getWindowManager());
		tv_3.startScroll();

	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 在此处添加执行的代码
			mTextView02.next();
			sCount++;
			if(sCount>=Integer.MAX_VALUE){
				sCount = str.size();
			}
			mTextView02.setText(str.get(sCount % (str.size())));
			if (str.size()>1) {
				handler.postDelayed(this, 5000);// 50是延时时长
			}
			
		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.next:
			mTextView02.next();
			sCount++;
			break;
		case R.id.prev:
			mTextView02.previous();
			sCount--;
			break;
		}
		// mTextView02.setText(sCount%2==0 ?
		// sCount+"AAFirstAA" :
		// sCount+"BBBBBBB");
		mTextView02.setText(str.get(sCount % 3));
		System.out.println("getH: [" + mTextView02.getHeight() + "]");

	}
}
