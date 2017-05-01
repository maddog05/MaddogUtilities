package com.maddog05.maddogutilities.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by andreetorres on 1/05/17.
 */

public class Numbers {

    /**
     * String value of integer with 2 numbers for number minor to 10.
     *
     * @param n, number to format.
     * @return number formatted.
     */
    public static String formatIntegerTwoNumbers(int n) {
        return String.format("%02d", n);
    }

    /**
     * String value of double showing 2 decimals.
     *
     * @param d, number to format.
     * @return number formatted.
     */
    public static String formatDoubleTwoDecimals(double d) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(d);
    }

    /**
     * Check if year is leap year.
     *
     * @param year, year to validate.
     * @return true if year is leap year, false otherwise.
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
    }
}
