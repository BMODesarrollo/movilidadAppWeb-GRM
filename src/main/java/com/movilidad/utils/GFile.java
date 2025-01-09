/*
 * GFile.java
 *
 * Created on 7 de octubre de 2007, 21:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.movilidad.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Camilo Andres Buelvas Castillo
 */
public class GFile {

    public static Object loadObject(String nameFile, String dir) throws ClassNotFoundException, IOException {
        Object obj = null;
        File file = new File(dir, nameFile);
        ObjectInputStream in = null;
        try {
            if (file.exists()) {
                in = new ObjectInputStream(new FileInputStream(file));
                obj = in.readObject(); //leo objeto y lo almaceno en una variable
            }
        } finally {
            if (in != null) {
                in.close(); //cerrar cuando el archivo no existe y retorna
            }
        }
        return obj;
    }

    public static void saveFile(byte[] obj,String nameFile, String dir) throws IOException {
        DataOutputStream out = null;
        try {
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                file = new File(dir, nameFile);
                out = new DataOutputStream(new FileOutputStream(file));
                out.write(obj);
            }
        } finally {
            out.close();
        }
    }
    
    

    public static void saveFileException(Throwable ex) {
        DataOutputStream out = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        try {
            File file = new File(System.getProperty("user.dir") + "/logs");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                file = new File(file.getPath(), df.format(new Date()) + ".log");
                out = new DataOutputStream(new FileOutputStream(file));
                ex.printStackTrace(new PrintStream(out));
                ex.printStackTrace();
            }
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex1) {
//                    Logger.getLogger(GFile.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
}
