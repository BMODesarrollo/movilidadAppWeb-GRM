/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.dto;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jimen
 */
@XmlRootElement
public class EmpleadoDescansoDTO {

    @Column(name = "id_empleado")
    private int idEmpleado;
    @Column(name = "total_dias_descanso")
    private int totalDiasDescanso;

    public EmpleadoDescansoDTO(int idEmpleado, int totalDiasDescanso) {
        this.idEmpleado = idEmpleado;
        this.totalDiasDescanso = totalDiasDescanso;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getTotalDiasDescanso() {
        return totalDiasDescanso;
    }

    public void setTotalDiasDescanso(int totalDiasDescanso) {
        this.totalDiasDescanso = totalDiasDescanso;
    }

}
