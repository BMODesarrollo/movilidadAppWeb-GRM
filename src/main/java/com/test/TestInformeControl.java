package com.test;

import com.movilidad.util.beans.InformeControl;
import com.movilidad.utils.Util;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Ballestas
 */
public class TestInformeControl {

    public static void main(String[] args) {
        Map parametros = new HashMap();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(2019,01,10));
        int numberOfDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("Días del mes : " + numberOfDays);

        for (int i = 1; i <= numberOfDays; i++) {
//            fechas.add(new Fechas(Util.dateFormat(c.getTime())));
//            lst.add(new InformeControl(Util.dateFormat(c.getTime())));
//            parametros.put(("resumen" + i), new InformeControl(Util.toDate(Util.dateFormat(c.getTime()))));
            c.add(Calendar.DATE, 1);
        }
    }
    

}
