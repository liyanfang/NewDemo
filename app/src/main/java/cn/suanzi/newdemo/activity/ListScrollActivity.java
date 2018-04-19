package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.View.SildingFinishLayout;

/**
 * 解决listView和ScrollView滑动冲突问题Demo
 * Created by liyanfang on 2017/7/13.
 */
public class ListScrollActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_list_scroll_view);
        ListView listView = (ListView) findViewById(R.id.lv_list);
        View topView = LayoutInflater.from(this).inflate(R.layout.list_head_view, null);
        listView.addHeaderView(topView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, initData());
        listView.setAdapter(arrayAdapter);

        SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.ly_text);
        mSildingFinishLayout
                .setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

                    @Override
                    public void onSildingFinish() {
                        ListScrollActivity.this.finish();
                    }
                });

        // touchView要设置到ListView上面
        mSildingFinishLayout.setTouchView(listView);

    }

    private List<String> initData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            data.add("第" + i + "条测试数据balabalabalabala……");
        }
        return data;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.right_out);
    }
}
