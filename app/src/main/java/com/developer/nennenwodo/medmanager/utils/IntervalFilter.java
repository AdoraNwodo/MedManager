package com.developer.nennenwodo.medmanager.utils;

import android.text.InputFilter;
import android.text.Spanned;


/**
 * Validates input. Makes sure input is in a specific range and can produce equal daily interval
 */
public class IntervalFilter implements InputFilter {

    private int min, max;

    public IntervalFilter(){}

    public IntervalFilter(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input) && canProduceEqualDailyInterval(input)) return null;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return "";
    }

    /**
     * Returns true if the input value is between minNum and maxNum
     * @param minNum
     * @param maxNum
     * @param input
     * @return
     */
    public boolean isInRange(int minNum, int maxNum, int input) {
        return maxNum > minNum ? input >= minNum && input <= maxNum : input >= maxNum && input <= minNum;
    }

    /**
     * Returns true if the input value is can produce equal daily timely intervals
     * @param input
     * @return
     */
    public boolean canProduceEqualDailyInterval(int input) {
        return ((24 % input) == 0);
    }
}
