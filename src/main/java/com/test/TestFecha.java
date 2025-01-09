package com.test;

import com.movilidad.utils.Util;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Carlos Ballestas
 */
public class TestFecha {

    static String b1;
    static String b2;
    static String b3;
    static String b4;
    static String b5;
    static String b6;
    static String b7;

    public static void main(String[] args) throws ParseException {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println(df.format(new BigDecimal(992848.00)));
        System.out.println(df.format(new BigDecimal(123456.00)));
        System.out.println(df.format(new BigDecimal(123456123456.78)));
//        ListaFechas(Util.toDate("2019-07-22"));
    }

    public static void ListaFechas(Date fecha) throws ParseException {
        Date fromDate = fecha;
        int c = 0;
        Date toDate;
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        current.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        toDate = current.getTime();
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        while (!current.getTime().after(toDate) && c <= 6) {
            current.add(Calendar.DATE, -1);
            switch (c) {
                case 0:
                    b7 = Util.dateFormat(current.getTime());
                    System.out.println(b7);
                    break;
                case 1:
                    b6 = Util.dateFormat(current.getTime());
                    System.out.println(b6);
                    break;
                case 2:
                    b5 = Util.dateFormat(current.getTime());
                    System.out.println(b5);
                    break;
                case 3:
                    b4 = Util.dateFormat(current.getTime());
                    System.out.println(b4);
                    break;
                case 4:
                    b3 = Util.dateFormat(current.getTime());
                    System.out.println(b3);
                    break;
                case 5:
                    b2 = Util.dateFormat(current.getTime());
                    System.out.println(b2);
                    break;
                case 6:
                    b1 = Util.dateFormat(current.getTime());
                    System.out.println(b1);
                    break;
            }
            c++;
        }
    }

    public static String getB1() {
        return b1;
    }

    public static void setB1(String b1) {
        TestFecha.b1 = b1;
    }

    public static String getB2() {
        return b2;
    }

    public static void setB2(String b2) {
        TestFecha.b2 = b2;
    }

    public static String getB3() {
        return b3;
    }

    public static void setB3(String b3) {
        TestFecha.b3 = b3;
    }

    public static String getB4() {
        return b4;
    }

    public static void setB4(String b4) {
        TestFecha.b4 = b4;
    }

    public static String getB5() {
        return b5;
    }

    public static void setB5(String b5) {
        TestFecha.b5 = b5;
    }

    public static String getB6() {
        return b6;
    }

    public static void setB6(String b6) {
        TestFecha.b6 = b6;
    }

    public static String getB7() {
        return b7;
    }

    public static void setB7(String b7) {
        TestFecha.b7 = b7;
    }

}
