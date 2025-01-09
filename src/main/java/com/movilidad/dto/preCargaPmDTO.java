/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.dto;

import com.movilidad.model.Empleado;
import com.movilidad.model.PmGrupo;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author julian.arevalo
 */
public class preCargaPmDTO implements Serializable {
    
    private String operador;
    private String grupoName;
    private PmGrupo grupo;
    private Empleado empleado;

    public preCargaPmDTO() {
    }

    public preCargaPmDTO(String operador, String grupoName, PmGrupo grupo, Empleado empleado) {
        this.operador = operador;
        this.grupoName = grupoName;
        this.grupo = grupo;
        this.empleado = empleado;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getGrupoName() {
        return grupoName;
    }

    public void setGrupoName(String grupoName) {
        this.grupoName = grupoName;
    }

    public PmGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(PmGrupo grupo) {
        this.grupo = grupo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}
