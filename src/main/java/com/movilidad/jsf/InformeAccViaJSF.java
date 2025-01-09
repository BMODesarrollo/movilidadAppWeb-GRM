/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author HP
 */
@Named(value = "informeAccViaJSF")
@ViewScoped
public class InformeAccViaJSF implements Serializable {

    private String c_firma = "";

    public InformeAccViaJSF() {
    }

    public void prueba() {
        if (!c_firma.equals("")) {
            System.out.println("Tamaño: " + c_firma.length());
        }
    }

    public String getC_firma() {
        return c_firma;
    }

    public void setC_firma(String c_firma) {
        this.c_firma = c_firma;
    }

}
