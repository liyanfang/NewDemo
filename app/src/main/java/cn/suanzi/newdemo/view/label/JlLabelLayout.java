package cn.suanzi.newdemo.view.label;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.R;

/**
 *
 * @author liyanfang
 * @date 2019/6/12 下午3:45
 * @version 1.0.0
 * @description 自定义行数显示label
 */
public class JlLabelLayout extends FlexboxLayout {

    private List<LabelModel> labelList = new ArrayList<>();

    private Paint paint = new Paint();
    /** 测量LabelLayout的宽度*/
    private int measuredWidth = 0;
    /** 测量LabelLayout的高度*/
    private int measuredHeight = 0;
    /** label的行数*/
    private int labelLine;
    /** label的高度*/
    private int labelHeight;
    /** label的高度*/
    private int labelWidth;
    /** label的内边距*/
    private int labelPaddingLeft;
    /** label的内边距*/
    private int labelPaddingRight;
    /** 文字大小*/
    private float textSize = sp2px(14);
    /** label的间隙*/
    private int labelSpace;
    /** label 底部间隙*/
    private int labelMarginBottom;

    private boolean isFirst = true;


    private OnLabelClickListener onClickListener;

    public void setOnLabelClickListener(OnLabelClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public JlLabelLayout(Context context) {
        super(context);
        initData();
    }

    public JlLabelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        initData();
    }

    public JlLabelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        initData();
    }

    private void initView(Context context, AttributeSet attrs) {
        this.setFlexWrap(FlexWrap.WRAP);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.JlLabelLayout);
        // 背景颜色
        labelLine = ta.getInt(R.styleable.JlLabelLayout_jl_label_line, 0);
        labelHeight = (int) ta.getDimension(R.styleable.JlLabelLayout_jl_label_height, 0);
        labelWidth = (int) ta.getDimension(R.styleable.JlLabelLayout_jl_label_width, 0);
        labelPaddingLeft = (int) ta.getDimension(R.styleable.JlLabelLayout_jl_label_padding_left, 0);
        labelPaddingRight = (int) ta.getDimension(R.styleable.JlLabelLayout_jl_label_padding_right, 0);
        textSize = ta.getDimension(R.styleable.JlLabelLayout_jl_label_text_size, 0);
        labelSpace = (int) ta.getDimension(R.styleable.JlLabelLayout_jl_label_space, 0);
        labelMarginBottom = (int) ta.getDimension(R.styleable.JlLabelLayout_jl_label_margin_bottom, 0);
        ta.recycle();
        // 设置画笔text的size
        paint.setTextSize(textSize);
    }

    private void initData() {
        labelList.add(new LabelModel("年终大促年终大促年终大促年终大促年终大促年终大促年终大促年终大促年终大促年终大促年终大促年终大促年终大促年终大促", Color.WHITE, R.drawable.bg_label_red_broder));
        labelList.add(new LabelModel("全场包邮", Color.WHITE, R.drawable.bg_label_red));
        labelList.add(new LabelModel("健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康健康", Color.WHITE,  R.drawable.bg_label_green));
        labelList.add(new LabelModel("享活", Color.WHITE,  R.drawable.bg_label_red_broder));
        labelList.add(new LabelModel("跨会场满返", Color.WHITE,  R.drawable.bg_label_red));
        labelList.add(new LabelModel("1", Color.WHITE,  R.drawable.bg_label_red_broder));
        labelList.add(new LabelModel("1", Color.WHITE,  R.drawable.bg_label_red_broder));
        labelList.add(new LabelModel("品质女装", Color.WHITE,  R.drawable.bg_label_green));
        labelList.add(new LabelModel("女装", Color.WHITE, R.drawable.bg_label_red));
        labelList.add(new LabelModel("衣服", Color.WHITE, R.drawable.bg_label_red_broder));
        labelList.add(new LabelModel("鞋子", Color.WHITE, R.drawable.bg_label_green));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth() - (this.getPaddingLeft() + this.getPaddingRight() + this.getPaddingEnd() + this.getPaddingStart());
        measuredHeight = getMeasuredHeight() - (this.getPaddingTop() + this.getPaddingBottom());
        if (isFirst) {
            isFirst = false;
            setLabelList(labelList);
        }


    }

    public void setLabelList(List<LabelModel> labelList) {
        if (labelList == null || labelList.size() == 0) {
            return;
        }
        this.removeAllViews();
        // label总宽度
        float labelAllWidth = 0;
        // 判断是否限定高度
        if (measuredHeight > 0) {
            float labelAllHeight;
            if (labelHeight > 0) {
                labelAllHeight = labelHeight + labelMarginBottom;
            } else {
                labelAllHeight = textSize + labelMarginBottom;
            }
            int line = (int) (measuredHeight/labelAllHeight);
            line = line == 0 ? 1 : line;
            labelLine = (labelLine == 0 || labelLine > line ) ? line : labelLine;
        }
        // 行数 * 控件宽度 = 总共需要显示的宽度
        int allWidth = measuredWidth * labelLine;
        // 测量label中text的宽度
        float measureLabelTextWidth = labelWidth + labelSpace;
        for (LabelModel label : labelList) {
            label.setLabelPaddingLeft(labelPaddingLeft > 0 ? labelPaddingLeft : label.getLabelPaddingLeft());
            label.setLabelPaddingRight(labelPaddingRight > 0 ? labelPaddingRight : label.getLabelPaddingRight());
            // 限制行数的计算
            if (labelLine > 0) {
                // 单个label的宽度
                if (labelWidth == 0) {
                    float measureText = paint.measureText(label.getText());
                    measureLabelTextWidth = labelSpace + label.getLabelPaddingRight() + label.getLabelPaddingLeft() + measureText;
                }

                if (measureLabelTextWidth > measuredWidth) {
                    // 判断label里的文字长度超过父布局的宽，默认显示父布局的宽
                    labelAllWidth += measuredWidth;
                    // 判断默认父布局的宽后，如果前一行的label宽度很小，需要补齐前一行的宽度
                    float sizeWidth = labelAllWidth % measuredWidth;
                    labelAllWidth = (sizeWidth > 0) ? (labelAllWidth + measuredWidth - sizeWidth) : labelAllWidth;
                } else {
                    labelAllWidth += measureLabelTextWidth;
                }
                if (allWidth < labelAllWidth) {
                    break;
                }
            }
            TextView textView = new TextView(getContext());
            LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (labelWidth > 0) {
                lparams.width = labelWidth;
            }
            if (labelHeight > 0) {
                lparams.height = labelHeight;
            }
            lparams.rightMargin = labelSpace;
            lparams.bottomMargin = labelMarginBottom;
            textView.setLayoutParams(lparams);
            textView.setText(label.getText());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(label.getTextColor());
            if (label.getBgShape() > 0){
                textView.setBackgroundResource(label.getBgShape());
            } else if (label.getGradientDrawable() != null){
                textView.setBackground(label.getGradientDrawable());
            } else {
                textView.setBackgroundColor(label.getBgColor());
            }
            textView.setPadding(label.getLabelPaddingLeft(), 0, label.getLabelPaddingRight(), 0);
            textView.setIncludeFontPadding(false);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setOnClickListener(view -> {
                if (onClickListener != null) {
                    onClickListener.onClick(textView);
                }
            });
            this.addView(textView);
        }
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    private int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (spValue * fontScale + 0.5f);
    }

    public interface OnLabelClickListener {
        void onClick(TextView view);
    }

}
