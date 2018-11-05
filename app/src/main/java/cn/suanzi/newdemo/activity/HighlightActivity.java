package cn.suanzi.newdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.Util.Util;
import cn.suanzi.newdemo.view.picker.picker.DatePicker;
import cn.suanzi.newdemo.view.picker.WheelView;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.position.OnRightPosCallback;
import zhy.com.highlight.position.OnTopPosCallback;
import zhy.com.highlight.shape.CircleLightShape;
import zhy.com.highlight.shape.RectLightShape;

public class HighlightActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HighlightActivity.class.getSimpleName();

    /** 高亮*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_hight_light);
        Button btnTest1 = findViewById(R.id.btn_test1);
        Button btnTest2 = findViewById(R.id.btn_test2);
        TextView btnTest3 = findViewById(R.id.btn_test3);
        View rootView = findViewById(R.id.ll_root_view);
        btnTest3.setOnClickListener(view -> initHighlight());
        new Thread(() -> {
            this.runOnUiThread(this::initHighlight);
        }).start();
        setIvadd();
    }
    boolean isBack = false;

    private void setIvadd () {
        ImageView ivAdd = findViewById(R.id.iv_add);
        LinearInterpolator lin = new LinearInterpolator();
        ivAdd.setOnClickListener(view -> {
            RotateAnimation animation1 = null;
            if (isBack) {
                isBack = false;
                animation1 = new RotateAnimation(45f, 0f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation1.setDuration(150); // 设置动画时间
//                Animation animation =  AnimationUtils.loadAnimation(this, R.anim.anim1);
                animation1.setInterpolator(new AccelerateInterpolator());
                animation1.setFillAfter(true);
                ivAdd.startAnimation(animation1);
            } else {
                isBack = true;
                animation1 = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation1.setInterpolator(new AccelerateInterpolator());
                animation1.setDuration(150); // 设置动画时间
                animation1.setFillAfter(true);
                ivAdd.startAnimation(animation1);
            }

        });
    }

    private void initHighlight () {
        HighLight highLight = new HighLight(this);
        highLight.addHighLight(R.id.btn_test1, R.layout.highlight_meeting_list, new OnRightPosCallback(45), new RectLightShape())
                .addHighLight(R.id.btn_test2, R.layout.highlight_msg, (rightMargin, bottomMargin, rectF, marginInfo) -> {
                    marginInfo.rightMargin = rightMargin + rectF.width() / 5;
                    marginInfo.bottomMargin = bottomMargin + rectF.height() + dp2px(10);
                },new RectLightShape(0,0,0, dp2px(20),dp2px(20)))
                .addHighLight(R.id.btn_test3, R.layout.highlight_find, (rightMargin, bottomMargin, rectF, marginInfo) -> {
                    marginInfo.rightMargin = rightMargin - dp2px(67) + rectF.width()/2;
                    marginInfo.bottomMargin = bottomMargin + rectF.height() * 2 / 3 + dp2px(15);
                }, new CircleLightShape(0, 0,0 ))
                .setClickCallback(() -> {
                    onNext();
                });
        highLight.show();
    }

    private void onNext () {
        HighLight highLight = new HighLight(this).addHighLight(R.id.btn_test5,R.layout.highlight_batch,new OnTopPosCallback(45),new RectLightShape());
        highLight.setClickCallback(() -> {
            // 结束啦
        });
        highLight.show();
    }

    private float dp2px (int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
    @Override
    public void onClick(View v) {
    }

    public void onShowDialog (View view) {
            final DatePicker picker = new DatePicker(this);
            picker.setCanceledOnTouchOutside(true);
            picker.setUseWeight(true);
            picker.setTopPadding(Util.dip2px(this, 10));
            picker.setRangeEnd(2111, 1, 11);
            picker.setRangeStart(2016, 8, 29);
            picker.setSelectedItem(2050, 10, 14);
            picker.setResetWhileWheel(false);
            picker.setLabel("","","");
            picker.setTitleTextColor(0);
            picker.setContentPadding(Util.dip2px(this, 15), 0);
            picker.setTextSize(18);
            picker.setCancelTextSize(15);
            picker.setCancelTextColor(Color.parseColor("#999999"));
            picker.setSubmitTextSize(15);
            picker.setSubmitTextColor(Color.parseColor("#FF514C"));
            picker.setTextColor(Color.parseColor("#333333"), Color.parseColor("#DDDDDD"));
            picker.setTopLineVisible(false);
            WheelView.DividerConfig dividerConfig = new WheelView.DividerConfig();
            dividerConfig.setColor(Color.parseColor("#FF514C"));
            dividerConfig.setThick(Util.dip2px(this, 2));
            picker.setDividerConfig(dividerConfig);
            picker.setHeight(Util.dip2px(this, 235));
            picker.setLineSpaceMultiplier(3);

            picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = year + "-" + month + "-" + day;
                    Toast.makeText(HighlightActivity.this, date, Toast.LENGTH_SHORT).show();
                }
            });
            picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                @Override
                public void onYearWheeled(int index, String year) {
//                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                }

                @Override
                public void onMonthWheeled(int index, String month) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                }

                @Override
                public void onDayWheeled(int index, String day) {
//                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                }
            });
            picker.show();
    }

}
