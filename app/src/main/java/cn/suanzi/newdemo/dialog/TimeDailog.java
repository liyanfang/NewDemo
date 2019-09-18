package cn.suanzi.newdemo.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import cn.suanzi.newdemo.R;
import cn.suanzi.newdemo.view.picker.WheelView;
import cn.suanzi.newdemo.view.picker.picker.DateTimePicker;
import cn.suanzi.newdemo.view.picker.util.DateUtils;
import cn.suanzi.newdemo.view.picker.util.LogUtils;

import static cn.suanzi.newdemo.view.picker.popup.BasicPopup.WRAP_CONTENT;

public class TimeDailog extends BaseDialog {

    private static final String TAG = TimeDailog.class.getSimpleName();

    /**
     * 不显示
     */
    public static final int NONE = -1;

    /**
     * 年月日
     */
    public static final int YEAR_MONTH_DAY = 0;
    /**
     * 年月
     */
    public static final int YEAR_MONTH = 1;

    /**
     * 月日
     */
    public static final int MONTH_DAY = 2;
    /** 显示布局的layout*/
    private LinearLayout llTimePicker;

    /** 显示的数据*/
    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();

    /**
     * 选择年月后，数据是否需要重置到1月1号
     */
    private boolean resetWhileWheel = false;

    private int selectedYearIndex = 0, selectedMonthIndex = 0, selectedDayIndex = 0;

    private int startYear = 2018, startMonth = 9, startDay = 1;
    private int endYear = 2020, endMonth = 12, endDay = 31;
    /** 时间显示模型*/
    private int dateMode = YEAR_MONTH;

    private int textSize = WheelView.TEXT_SIZE;
    private int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
    private int textColorFocus = WheelView.TEXT_COLOR_FOCUS;
    private WheelView.DividerConfig dividerConfig = new WheelView.DividerConfig();

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColorNormal() {
        return textColorNormal;
    }

    public void setTextColorNormal(int textColorNormal) {
        this.textColorNormal = textColorNormal;
    }

    public int getTextColorFocus() {
        return textColorFocus;
    }

    public void setTextColorFocus(int textColorFocus) {
        this.textColorFocus = textColorFocus;
    }

    public WheelView.DividerConfig getDividerConfig() {
        return dividerConfig;
    }

    public void setDividerConfig(WheelView.DividerConfig dividerConfig) {
        this.dividerConfig = dividerConfig;
    }

    /** 选择时间回调*/
    private OnWheelListener onWheelListener;

    /**
     * 点击回调
     * @param onWheelListener
     */
    public void setOnWheelListener(OnWheelListener onWheelListener) {
        this.onWheelListener = onWheelListener;
    }

    /**
     * 重置数据到1月1号
     * @param resetWhileWheel true 重置，false 不重置
     */
    public void setResetWhileWheel(boolean resetWhileWheel) {
        this.resetWhileWheel = resetWhileWheel;
    }

    public TimeDailog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_time_picker;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setGravity(Window window, int gravity) {
        super.setGravity(window, Gravity.BOTTOM);
    }

    @Override
    protected void initView() {
        llTimePicker = findViewById(R.id.ll_time_picker);
        makeCenterView();
    }

    /**
     * 绘制中间中间时间选择器
     */
    private void makeCenterView() {
        // 如果未设置默认项，则需要在此初始化数据
        if ((dateMode == YEAR_MONTH_DAY || dateMode == YEAR_MONTH) && years.size() == 0) {
            LogUtils.verbose(this, "init years before make view");
            initYearData();
        }
        if (dateMode != NONE && months.size() == 0) {
            LogUtils.verbose(this, "init months before make view");
            int selectedYear = DateUtils.trimZero(getSelectedYear());
            changeMonthData(selectedYear);
        }
        if ((dateMode == YEAR_MONTH_DAY || dateMode == MONTH_DAY) && days.size() == 0) {
            LogUtils.verbose(this, "init days before make view");
            int selectedYear;
            if (dateMode == YEAR_MONTH_DAY) {
                selectedYear = DateUtils.trimZero(getSelectedYear());
            } else {
                selectedYear = Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR);
            }
            int selectedMonth = DateUtils.trimZero(getSelectedMonth());
            changeDayData(selectedYear, selectedMonth);
        }

        final WheelView yearView = createWheelView();
        final WheelView monthView = createWheelView();
        final WheelView dayView = createWheelView();

        if (dateMode == YEAR_MONTH_DAY || dateMode == YEAR_MONTH) {
            yearView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            yearView.setItems(years, selectedYearIndex);
            yearView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                @Override
                public void onSelected(int index) {
                    selectedYearIndex = index;
                    String selectedYearStr = years.get(selectedYearIndex);
                    if (onWheelListener != null) {
                        onWheelListener.onYearWheeled(selectedYearIndex, selectedYearStr);
                    }
                    LogUtils.verbose(this, "change months after year wheeled");
                    if (resetWhileWheel) {
                        //重置月份索引
                        selectedMonthIndex = 0;
                        //重置日子索引
                        selectedDayIndex = 0;
                    }
                    //需要根据年份及月份动态计算天数
                    int selectedYear = DateUtils.trimZero(selectedYearStr);
                    changeMonthData(selectedYear);
                    monthView.setItems(months, selectedMonthIndex);
                    if (onWheelListener != null) {
                        onWheelListener.onMonthWheeled(selectedMonthIndex, months.get(selectedMonthIndex));
                    }
                    changeDayData(selectedYear, DateUtils.trimZero(months.get(selectedMonthIndex)));
                    dayView.setItems(days, selectedDayIndex);
                    if (onWheelListener != null) {
                        onWheelListener.onDayWheeled(selectedDayIndex, days.get(selectedDayIndex));
                    }
                }
            });
            llTimePicker.addView(yearView);
        }

        if (dateMode != NONE) {
            monthView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
            monthView.setItems(months, selectedMonthIndex);
            monthView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                @Override
                public void onSelected(int index) {
                    selectedMonthIndex = index;
                    String selectedMonthStr = months.get(selectedMonthIndex);
                    if (onWheelListener != null) {
                        onWheelListener.onMonthWheeled(selectedMonthIndex, selectedMonthStr);
                    }
                    if (dateMode == YEAR_MONTH_DAY || dateMode == MONTH_DAY) {
                        LogUtils.verbose(this, "change days after month wheeled");
                        if (resetWhileWheel) {
                            selectedDayIndex = 0;//重置日子索引
                        }
                        int selectedYear;
                        if (dateMode == YEAR_MONTH_DAY) {
                            selectedYear = DateUtils.trimZero(getSelectedYear());
                        } else {
                            selectedYear = Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR);
                        }
                        changeDayData(selectedYear, DateUtils.trimZero(selectedMonthStr));
                        dayView.setItems(days, selectedDayIndex);
                        if (onWheelListener != null) {
                            onWheelListener.onDayWheeled(selectedDayIndex, days.get(selectedDayIndex));
                        }
                    }
                }
            });
            llTimePicker.addView(monthView);
        }

        if (dateMode == YEAR_MONTH_DAY || dateMode == MONTH_DAY) {
            dayView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
            dayView.setItems(days, selectedDayIndex);
            dayView.setOnItemSelectListener(index -> {
                selectedDayIndex = index;
                if (onWheelListener != null) {
                    onWheelListener.onDayWheeled(selectedDayIndex, days.get(selectedDayIndex));
                }
            });
            llTimePicker.addView(dayView);
        }
    }

    private WheelView createWheelView() {
        WheelView wheelView = new WheelView(getContext());
        wheelView.setLineSpaceMultiplier(WheelView.LINE_SPACE_MULTIPLIER);
        wheelView.setTextPadding(WheelView.TEXT_PADDING);
        wheelView.setTextSize(textSize);
        wheelView.setTypeface(Typeface.DEFAULT);
        wheelView.setTextColor(textColorNormal, textColorFocus);
        wheelView.setDividerConfig(dividerConfig);
        wheelView.setOffset(WheelView.ITEM_OFF_SET);
        wheelView.setCycleDisable(true);
        wheelView.setUseWeight(true);
        wheelView.setTextSizeAutoFit(true);
        wheelView.setVisibleItemCount(5);
        return wheelView;
    }

    private int findItemIndex(ArrayList<String> items, int item) {
        //折半查找有序元素的索引
        int index = Collections.binarySearch(items, item, (lhs, rhs) -> {
            String lhsStr = lhs.toString();
            String rhsStr = rhs.toString();
            lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1) : lhsStr;
            rhsStr = rhsStr.startsWith("0") ? rhsStr.substring(1) : rhsStr;
            try {
                return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        });
        if (index < 0) {
            throw new IllegalArgumentException("Item[" + item + "] out of range");
        }
        return index;
    }

    /**
     * 初始化年份
     */
    private void initYearData() {
        years.clear();
        if (startYear == endYear) {
//            years.add(String.valueOf(startYear));
            years.add(getYearString(startYear));
        } else if (startYear < endYear) {
            //年份正序
            for (int i = startYear; i <= endYear; i++) {
//                years.add(String.valueOf(i));
                years.add(getYearString(i));
            }
        } else {
            //年份逆序
            for (int i = startYear; i >= endYear; i--) {
//                years.add(String.valueOf(i));
                years.add(getYearString(i));
            }
        }
        if (!resetWhileWheel) {
            if (dateMode == YEAR_MONTH_DAY || dateMode == YEAR_MONTH) {
                String year = DateUtils.fillZero(Calendar.getInstance().get(Calendar.YEAR));
                year = getYearString(year);
                int index = years.indexOf(year);
                if (index == -1) {
                    //当前设置的年份不在指定范围，则默认选中范围开始的年
                    selectedYearIndex = 0;
                } else {
                    selectedYearIndex = index;
                }
            }
        }
    }

    private String getYearString (Object year) {
        return String.format(Locale.CHINA, "%s年", year);
    }

    private String getMonthString (Object month) {
        return String.format(Locale.CHINA, "%s月", month);
    }

    private String getDayString (Object day) {
        return String.format(Locale.CHINA, "%s日", day);
    }

    /**
     * 初始化月份
     * @param selectedYear
     */
    private void changeMonthData(int selectedYear) {
        String preSelectMonth = "";
        if (!resetWhileWheel) {
            if (months.size() > selectedMonthIndex) {
                preSelectMonth = months.get(selectedMonthIndex);
            } else {
                String month = DateUtils.fillZero(Calendar.getInstance().get(Calendar.MONTH) + 1);
                month = getMonthString(month);
                preSelectMonth = month;
            }
            Log.d(TAG, "changeMonthData:  preSelectMonth=" + preSelectMonth);
        }
        months.clear();
        if (startMonth < 1 || endMonth < 1 || startMonth > 12 || endMonth > 12) {
            throw new IllegalArgumentException("Month out of range [1-12]");
        }
        if (startYear == endYear) {
            if (startMonth > endMonth) {
                for (int i = endMonth; i >= startMonth; i--) {
                    months.add(getMonthString(DateUtils.fillZero(i)));
                }
            } else {
                for (int i = startMonth; i <= endMonth; i++) {
                    months.add(getMonthString(DateUtils.fillZero(i)));
                }
            }
        } else if (selectedYear == startYear) {
            for (int i = startMonth; i <= 12; i++) {
                months.add(getMonthString(DateUtils.fillZero(i)));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                months.add(getMonthString(DateUtils.fillZero(i)));
            }
        } else {
            for (int i = 1; i <= 12; i++) {
                months.add(getMonthString(DateUtils.fillZero(i)));
            }
        }
        if (!resetWhileWheel) {
            //当前设置的月份不在指定范围，则默认选中范围开始的月份
            int preSelectMonthIndex = months.indexOf(preSelectMonth);
            selectedMonthIndex = preSelectMonthIndex == -1 ? 0 : preSelectMonthIndex;
        }
    }

    /**
     * 初始化天数
     * @param selectedYear
     * @param selectedMonth
     */
    private void changeDayData(int selectedYear, int selectedMonth) {
        int maxDays = DateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
        String preSelectDay = "";
        if (!resetWhileWheel) {
            if (selectedDayIndex >= maxDays) {
                //如果之前选择的日是之前年月的最大日，则日自动为该年月的最大日
                selectedDayIndex = maxDays - 1;
            }
            if (days.size() > selectedDayIndex) {
                //年或月变动时，保持之前选择的日不动
                preSelectDay = days.get(selectedDayIndex);
            } else {
                String day = DateUtils.fillZero(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                preSelectDay = getDayString(day);
            }
            Log.d(TAG, "changeDayData:  maxDays=" + maxDays + ", preSelectDay=" + preSelectDay);
        }
        days.clear();
        if (selectedYear == startYear && selectedMonth == startMonth
                && selectedYear == endYear && selectedMonth == endMonth) {
            //开始年月及结束年月相同情况
            for (int i = startDay; i <= endDay; i++) {
                days.add(getDayString(DateUtils.fillZero(i)));
            }
        } else if (selectedYear == startYear && selectedMonth == startMonth) {
            //开始年月相同情况
            for (int i = startDay; i <= maxDays; i++) {
                days.add(getDayString(DateUtils.fillZero(i)));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            //结束年月相同情况
            for (int i = 1; i <= endDay; i++) {
                days.add(getDayString(DateUtils.fillZero(i)));
            }
        } else {
            for (int i = 1; i <= maxDays; i++) {
                days.add(getDayString(DateUtils.fillZero(i)));
            }
        }
        if (!resetWhileWheel) {
            //当前设置的日子不在指定范围，则默认选中范围开始的日子
            int preSelectDayIndex = days.indexOf(preSelectDay);
            selectedDayIndex = preSelectDayIndex == -1 ? 0 : preSelectDayIndex;
        }
    }

    public String getSelectedYear() {
        if (dateMode == YEAR_MONTH_DAY || dateMode == YEAR_MONTH) {
            if (years.size() <= selectedYearIndex) {
                selectedYearIndex = years.size() - 1;
            }
            return years.get(selectedYearIndex);
        }
        return "";
    }

    public String getSelectedMonth() {
        if (dateMode != NONE) {
            if (months.size() <= selectedMonthIndex) {
                selectedMonthIndex = months.size() - 1;
            }
            return months.get(selectedMonthIndex);
        }
        return "";
    }

    public String getSelectedDay() {
        if (dateMode == YEAR_MONTH_DAY || dateMode == MONTH_DAY) {
            if (days.size() <= selectedDayIndex) {
                selectedDayIndex = days.size() - 1;
            }
            return days.get(selectedDayIndex);
        }
        return "";
    }

    public interface OnWheelListener {

        void onYearWheeled(int index, String year);

        void onMonthWheeled(int index, String month);

        void onDayWheeled(int index, String day);
    }
}
