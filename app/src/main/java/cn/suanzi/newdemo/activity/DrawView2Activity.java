package cn.suanzi.newdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

import cn.suanzi.newdemo.R;

/**
 * Created by liyanfang on 2017/7/13.
 */
public class DrawView2Activity extends Activity {

    private Path mPath;
    private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_draw_view_2);
        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        initView();
//        imageView.setImageBitmap(creatArrows(dip2px(this, 10), dip2px(this, 30), 12));
        imageView.setImageBitmap(creatArrows());
    }

    private void  initView () {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.parseColor("#ea2406"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 绘制一个箭头
     * @param mWidth  控件高度
     * @param mHeight 控件宽
     * @param den     箭头杆空格数量
     * @return 一个向下的箭头
     */
    public Bitmap creatArrows(int mWidth, int mHeight, int den) {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        // 清空所有已经画过的path至原始状态。
        mPath.reset();
        mPaint.setStyle(Paint.Style.FILL);
        int amount = 0;
        for (int i = 1; i <= den; i++) {
            amount += i;
        }
        float start = mHeight * 2 / 3;
        float l = (float) ((mHeight - start) / (Math.sin(60 * Math.PI / 180) * 2));
        mPath.moveTo(mWidth / 2, mHeight);
        mPath.lineTo(mWidth / 2 - l, start);
        mPath.lineTo(mWidth / 2 + l, start);
        mPath.lineTo(mWidth / 2, mHeight);
        int count = 0;
        int last = 0;
        float left = mWidth / 2 - l / 2;
        float right = mWidth / 2 + l / 2;
        for (int i = den; i > 0; i--) {
            last = count;
            count += i;
            if (i % 2 == 0) {
                float top = start - start * last / (amount);
                float boom = start - start * count / (amount);
                // Path.Direction.CCW  逆时针 | Path.Direction.CW 顺时针
                mPath.addRect(left, top, right, boom, Path.Direction.CW);
            }
        }
        canvas.drawPath(mPath, mPaint);
        return bitmap;
    }

    /**
     * 绘制一个箭头
     */
    public Bitmap creatArrows() {
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        // 清空所有已经画过的path至原始状态。
        mPath.reset();
//        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ff0000"));
        RectF rectF = new RectF(0, 0, 0, 0);
        mPath.addRect(rectF, Path.Direction.CW);
        mPath.addArc(rectF,0,360);
        canvas.drawPath(mPath,mPaint);
        return bitmap;
    }

}
