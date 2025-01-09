/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Carlos Alberto
 */
public class PrgSerconParteTrabajo implements Serializable {

    private String timeOrigin;
    private String timeDestiny;
    private String hIni_Turno2;
    private String hFin_Turno2;
    private String hIni_Turno3;
    private String hFin_Turno3;
    private String parteTrabajo;
    private String sercon;
    private String codOperador;
    private Date fecha;

    public PrgSerconParteTrabajo() {
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    public String gethIni_Turno2() {
        return hIni_Turno2;
    }

    public void sethIni_Turno2(String hIni_Turno2) {
        this.hIni_Turno2 = hIni_Turno2;
    }

    public String gethFin_Turno2() {
        return hFin_Turno2;
    }

    public void sethFin_Turno2(String hFin_Turno2) {
        this.hFin_Turno2 = hFin_Turno2;
    }

    public String gethIni_Turno3() {
        return hIni_Turno3;
    }

    public void sethIni_Turno3(String hIni_Turno3) {
        this.hIni_Turno3 = hIni_Turno3;
    }

    public String gethFin_Turno3() {
        return hFin_Turno3;
    }

    public void sethFin_Turno3(String hFin_Turno3) {
        this.hFin_Turno3 = hFin_Turno3;
    }

    public String getParteTrabajo() {
        return parteTrabajo;
    }

    public void setParteTrabajo(String parteTrabajo) {
        this.parteTrabajo = parteTrabajo;
    }

    public String getSercon() {
        return sercon;
    }

    public void setSercon(String sercon) {
        this.sercon = sercon;
    }

    public String getCodOperador() {
        return codOperador;
    }

    public void setCodOperador(String codOperador) {
        this.codOperador = codOperador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
