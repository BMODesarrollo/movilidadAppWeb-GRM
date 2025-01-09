/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.movilidad.utils.MovilidadUtil;

/**
 *
 * @author Carlos Ballestas
 */
public class TestHoras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String h1 = "03:43:00";
        String h2 = "14:53:00";

        String hCambiar = MovilidadUtil.sumarHoraSrting(h1, "24:00:00");

        int dif = MovilidadUtil.diferencia(h2, hCambiar);
        String difString = MovilidadUtil.toTimeSec(dif);

        System.out.println("DIF NUM: " + dif);
        System.out.println("DIF STRING: " + difString);
        
        if (dif > MovilidadUtil.toSecs("12:00:00")) {
            System.out.println("========================================");
            System.out.println("SI CUMPLE");
            System.out.println("========================================");
        }else{
            System.out.println("========================================");
            System.out.println("NO CUMPLE");
            System.out.println("========================================");
        }

    }

}
