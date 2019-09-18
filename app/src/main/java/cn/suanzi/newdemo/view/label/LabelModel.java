package cn.suanzi.newdemo.view.label;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.DrawableRes;

public class LabelModel {
    private String text;
    /** 文字颜色*/
    private int textColor;
    /** 背景颜色*/
    private int bgColor;
    /** 背景样式*/
    private int bgShape;
    /** 背景样式drawable*/
    private GradientDrawable gradientDrawable;
    /** label的高度*/
    private float labelHeight;
    /** label的高度*/
    private float labelWidth;
    /** label左边内边距*/
    private int labelPaddingLeft;
    /** label右边内边距*/
    private int labelPaddingRight;

    public LabelModel() {
    }

    public LabelModel(String text, int textColor, @DrawableRes int bgShape) {
        this.text = text;
        this.textColor = textColor;
        this.bgShape = bgShape;
    }

    public LabelModel(String text, int textColor, GradientDrawable gradientDrawable) {
        this.text = text;
        this.textColor = textColor;
        this.gradientDrawable = gradientDrawable;
    }

    public GradientDrawable getGradientDrawable() {
        return gradientDrawable;
    }

    public void setGradientDrawable(GradientDrawable gradientDrawable) {
        this.gradientDrawable = gradientDrawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getBgShape() {
        return bgShape;
    }

    public void setBgShape(int bgShape) {
        this.bgShape = bgShape;
    }

    public float getLabelHeight() {
        return labelHeight;
    }

    public void setLabelHeight(float labelHeight) {
        this.labelHeight = labelHeight;
    }

    public float getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(float labelWidth) {
        this.labelWidth = labelWidth;
    }

    public int getLabelPaddingLeft() {
        return labelPaddingLeft;
    }

    public void setLabelPaddingLeft(int labelPaddingLeft) {
        this.labelPaddingLeft = labelPaddingLeft;
    }

    public int getLabelPaddingRight() {
        return labelPaddingRight;
    }

    public void setLabelPaddingRight(int labelPaddingRight) {
        this.labelPaddingRight = labelPaddingRight;
    }
}
