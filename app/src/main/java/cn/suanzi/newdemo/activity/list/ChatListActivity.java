package cn.suanzi.newdemo.activity.list;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.CountDownTextureView;
import cn.suanzi.newdemo.adapter.recycle.ChartAdapter;

/**
 *
 * @author liyanfang
 * @date 2018/9/10
 */

public class ChatListActivity extends Activity implements View.OnClickListener{

    private RecyclerView mRvChar;
    private EditText mEdtInput;
    private TextView mTvSend;

    ChartAdapter chartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat_list);
        initView();
        initData();
    }

    private void initView () {
        mRvChar = findViewById(R.id.rv_char);
        mRvChar.setLayoutManager(new LinearLayoutManager(this));
        mEdtInput = findViewById(R.id.edt_input);
        mTvSend = findViewById(R.id.tv_send);
        mTvSend.setOnClickListener(this);
        addTextChangedListener();
    }

    private void addTextChangedListener () {
        mEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(mEdtInput.getText())) {
                    mTvSend.setEnabled(false);
                    mTvSend.setBackgroundResource(R.drawable.shape_bg_gray);
                } else {
                    mTvSend.setEnabled(true);
                    mTvSend.setBackgroundResource(R.drawable.shape_bg_blue);
                }
            }
        });
    }

    private void initData () {
        setChartAdapter(null);
    }

    private void setChartAdapter (List<View> views) {
        if (chartAdapter == null) {
            chartAdapter = new ChartAdapter(this, views);
            mRvChar.setAdapter(chartAdapter);
        } else {
            chartAdapter.addDatas(views);
        }
        ((LinearLayoutManager) mRvChar.getLayoutManager()).scrollToPositionWithOffset(chartAdapter.getSize(), 0);
    }

    private void setChartAdapterItem (View views) {
        chartAdapter.addItem(views);
        Log.d("TAG", "setChartAdapterItem: " + chartAdapter.getSize());
        ((LinearLayoutManager) mRvChar.getLayoutManager()).scrollToPositionWithOffset(chartAdapter.getSize(), 0);
    }

    /**
     * 发送消息
     */
    private void sendMsg () {
        mTvSend.setEnabled(false);
        if (mEdtInput.getText().toString().contains("image")) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_image, null,false);
            ImageView ivImage = view.findViewById(R.id.iv_image);
            setChartAdapterItem(view);
        } else if (mEdtInput.getText().toString().contains("time")) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_dowmtext, null,false);
            CountDownTextureView countDownView = view.findViewById(R.id.count_down_view);
            TextView tvTime = view.findViewById(R.id.tv_time);
            long time = System.currentTimeMillis() + 60 * 1000;
            CountDownTimer countDownTimer = new CountDownTimer(15000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String value = String.valueOf((int) (millisUntilFinished / 1000));
                    tvTime.setText(value);
                }

                @Override
                public void onFinish() {
                    tvTime.setText("已完成");
                }
            };
            countDownTimer.start();
//            countDownView.setTime(time - System.currentTimeMillis());
            setChartAdapterItem(view);
        } else {
            View view = LayoutInflater.from(this).inflate(R.layout.item_text, null,false);
            TextView tvText = view.findViewById(R.id.tv_text);
            tvText.setText(mEdtInput.getText().toString());
            setChartAdapterItem(view);
        }
        mEdtInput.setText("");
        mTvSend.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                sendMsg();
                break;
            default:
        }
    }
}
