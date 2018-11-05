package cn.suanzi.newdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by zhanghongyang01 on 17/11/23.
 */

public class AdImageViewVersion1 extends AppCompatImageView {
    public AdImageViewVersion1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /** 矩形局域的坐标*/
    private RectF mBitmapRectF;
    private Bitmap mBitmap;

    /** 为控件高度*/
    private int mMinDy;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 控件高度为 180dp
        mMinDy = h;
        Log.d("TTG", "onSizeChanged: " + mMinDy + w);
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        mBitmap = drawableToBitamp(drawable);
        mBitmapRectF = new RectF(0, 0, w,  mBitmap.getHeight() * w / mBitmap.getWidth());
    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        // getIntrinsicWidth()和getIntrinsicHeight() 宽高单位： dp，根据手机分辨率来得
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /** 需要偏移位置*/
    private int mDy;

    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }
        // dy 是外面滑动距离 - 控件本生高度
        mDy = dy - mMinDy;
        if (mDy <= 0) {
            mDy = 0;
        }
        // mBitmapRectF.height()  控件当前y轴当前的位置
        if (mDy > mBitmapRectF.height() - mMinDy) {
            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        canvas.save();
        canvas.translate(0, -mDy);
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }


}
