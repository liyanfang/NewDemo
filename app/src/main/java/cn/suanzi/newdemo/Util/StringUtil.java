package cn.suanzi.newdemo.Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by liyanfang on 2018/11/5.
 */

public class StringUtil {

    /**
     * 去掉小数点后面多余的0
     * @param price
     * @return
     */
    public static String bigDecimal (double price) {
        try{
            if (price == 0) {
                return "0";
            }
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            BigDecimal a = new BigDecimal(decimalFormat.format(price));
            return a.stripTrailingZeros().toPlainString();
        }catch (Exception e) {
            return String.valueOf(price);
        }
    }

}
