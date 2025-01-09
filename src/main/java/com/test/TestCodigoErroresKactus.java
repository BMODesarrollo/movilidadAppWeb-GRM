package com.test;

import java.util.Arrays;

/**
 *
 * @author Carlos Ballestas
 */
public class TestCodigoErroresKactus {

    public static void main(String[] args) {
//        String mensajeError = "No hay datos en KNmValpe para el periodo actual : 126 \n"
//                + " No hay datos en KNmValpe para el periodo actual : 121 \n"
//                + " No hay datos en KNmValpe para el periodo actual : 122 \n"
//                + " No hay datos en KNmValpe para el periodo actual : 141 \n"
//                + " No hay datos en KNmValpe para el periodo actual : 124 \n"
//                + " No hay datos en KNmValpe para el periodo actual : 125 ";
//        
//        String[] results = mensajeError.replaceAll("No hay datos en KNmValpe para el periodo actual : ", "")
//                                    .trim()
//                                    .replaceAll(" ", "")
//                                    .split("\n");

        String codigoError = "122,";
        
        System.out.println(Arrays.toString(codigoError.split(",")));
    }

}
