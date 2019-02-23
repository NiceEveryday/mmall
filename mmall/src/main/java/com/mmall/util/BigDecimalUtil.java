package com.mmall.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    private BigDecimalUtil(){}

    public static BigDecimal add(double a,double b){
        BigDecimal bigDecimalA = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalB = new BigDecimal(Double.toString(b));
        return bigDecimalA.add(bigDecimalB);
    }


    public static BigDecimal sub(double a,double b){
        BigDecimal bigDecimalA = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalB = new BigDecimal(Double.toString(b));
        return bigDecimalA.subtract(bigDecimalB);
    }

    public static BigDecimal mul(double a,double b){
        BigDecimal bigDecimalA = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalB = new BigDecimal(Double.toString(b));
        return bigDecimalA.multiply(bigDecimalB);
    }

    public static BigDecimal div(double a,double b){
        BigDecimal bigDecimalA = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalB = new BigDecimal(Double.toString(b));
        return bigDecimalA.divide(bigDecimalB,BigDecimal.ROUND_HALF_UP);
    }
}
