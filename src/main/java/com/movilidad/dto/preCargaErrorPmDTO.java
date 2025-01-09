/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.dto;

import java.io.Serializable;

/**
 *
 * @author julian.arevalo
 */
public class preCargaErrorPmDTO implements Serializable {
    
    private String operador;
    private String grupo;
    private String motivo;

    public preCargaErrorPmDTO() {
    }
    
    public preCargaErrorPmDTO(String operador, String grupo, String motivo) {
        this.operador = operador;
        this.grupo = grupo;
        this.motivo = motivo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
