/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Ballestas
 */
public class MailConfig {
    public static Map<String,String> getMailParams(){
        Map<String, String> mapa = new HashMap<String, String>();        
        mapa.put("host", Util.getProperty("mailHost"));
        mapa.put("mailBcc", Util.getProperty("mailBcc"));
        mapa.put("from", Util.getProperty("mailFrom"));
        mapa.put("port", Util.getProperty("mailPort"));
        mapa.put("password", Util.getProperty("mailPassword"));
        return mapa;
    }
}
