/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;

/**
 *
 * @author solucionesit
 */
public class AuditoriaRespuestaDTO implements Serializable {

    private String respuesta;

    public AuditoriaRespuestaDTO() {
    }


    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
