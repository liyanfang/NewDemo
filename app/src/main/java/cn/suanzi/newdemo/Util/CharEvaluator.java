package cn.suanzi.newdemo.Util;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Created by liyanfang on 2018/1/5.
 */

public class CharEvaluator implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt  = (int)startValue;
        int endInt = (int)endValue;
        int curInt = (int)(startInt + fraction *(endInt - startInt));
        char result = (char)curInt;
        return result;
    }
}
