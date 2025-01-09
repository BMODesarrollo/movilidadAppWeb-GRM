/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author luis
 */
public class TestSendMail {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s=new Scanner(new File("/Users/alexanderosorio/test.txt"));
        while(s.hasNextLine()){
           String cad=s.nextLine();
            String[] split = cad.split(";");
//            System.out.println(split[2]);
        }
        // TODO code application logic here
        /*Map<String, String> mapa = new HashMap<String, String>();        
        mapa.put("host", MovilidadUtil.getProperty("mailHost"));
        mapa.put("from", MovilidadUtil.getProperty("mailFrom"));
        mapa.put("port", MovilidadUtil.getProperty("mailPort"));
        mapa.put("password", MovilidadUtil.getProperty("mailPassword"));
        mapa.put("template", MovilidadUtil.getProperty("templateNovedades"));

        List<String> adjuntos = new LinkedList<>();
        adjuntos.add("/home/luis/Imágenes/Mozilla Firefox_258.png");
        adjuntos.add("/home/luis/Imágenes/Mozilla Firefox_259.png");
        adjuntos.add("/home/luis/Imágenes/Mozilla Firefox_260.png");
        adjuntos.add("/home/luis/Imágenes/Mozilla Firefox_261.png");
        adjuntos.add("/home/luis/Imágenes/Mozilla Firefox_263.png");
        adjuntos.add("/home/luis/Imágenes/Mozilla Firefox_284.png");

        Map mailProperties = new HashMap();
        mailProperties.put("tipo", "Ausentismo");
        mailProperties.put("detalle", "Licencia de Luto");
        mailProperties.put("operador", "Thanos Antonio");
        mailProperties.put("vehiculo", "T1101");
        mailProperties.put("username", "admin");
        mailProperties.put("generada", Util.dateTimeFormat(new Date()));
        mailProperties.put("observaciones", "Gracias por reenviarme el correo.\n"
                + "\n"
                + "Las recomendaciones siguen siendo las mismas:\n"
                + "Garantizar que los buses entregados tengan correctamente instalada la platina donde se instalarán/fijarán los torniquetes (área y grosor)\n"
                + "La disposición de los vehículos en un mismo lugar y que este cuente con rampa o cárcamos para la intervención de los vehículos\n"
                + "Conexiones eléctricas 110v de fácil acceso desde el área de las rampas/cárcamos. En caso de fallas eléctricas, suministrar generador eléctrico con combustible con la suficiente potencia para mover el taladro");

        SendMails.sendEmail(mapa, mailProperties, "Prueba Envío Rigel", 
                "Andrés, esto es una prueba", "luis.velezl@hotmail.com,proyectos@solucio
        nes-it.com.co,jimenezjesuz@outlook.com", 
                "Notificaciones RIGEL", adjuntos);*/

    }

}
