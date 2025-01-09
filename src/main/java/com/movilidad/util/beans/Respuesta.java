/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author solucionesit
 */
public class Respuesta implements Serializable {

    private List<AuditoriaRespuestaDTO> listaRespuesta;
    private String fecha;
    private String quienAuditoo;
    private String seAuditoo;

    public List<AuditoriaRespuestaDTO> getListaRespuesta() {
        return listaRespuesta;
    }

    public void setListaRespuesta(List<AuditoriaRespuestaDTO> listaRespuesta) {
        this.listaRespuesta = listaRespuesta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getQuienAuditoo() {
        return quienAuditoo;
    }

    public void setQuienAuditoo(String quienAuditoo) {
        this.quienAuditoo = quienAuditoo;
    }

    public String getSeAuditoo() {
        return seAuditoo;
    }

    public void setSeAuditoo(String seAuditoo) {
        this.seAuditoo = seAuditoo;
    }

}
