/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;

/**
 * Esta clase ayuda a la presentanción y trabajo de los servicios al realizar
 * una gestions de servicios desde el panel principal
 *
 * @author solucionesit
 */
public class ArbolViewPrgTc implements Serializable {

    private String nombre;
    private PrgTc objPrgTc;
    private PrgPattern objPrgPattern;
    private int nivel;

    public ArbolViewPrgTc(String nombre, PrgTc objPrgTc, int nivel) {
        this.nombre = nombre;
        this.objPrgTc = objPrgTc;
        this.nivel = nivel;
    }

    public ArbolViewPrgTc(String nombre, PrgTc objPrgTc, PrgPattern objPrgPattern, int nivel) {
        this.nombre = nombre;
        this.objPrgTc = objPrgTc;
        this.objPrgPattern = objPrgPattern;
        this.nivel = nivel;
    }

    public PrgPattern getObjPrgPattern() {
        return objPrgPattern;
    }

    public void setObjPrgPattern(PrgPattern objPrgPattern) {
        this.objPrgPattern = objPrgPattern;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PrgTc getObjPrgTc() {
        return objPrgTc;
    }

    public void setObjPrgTc(PrgTc objPrgTc) {
        this.objPrgTc = objPrgTc;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

}
