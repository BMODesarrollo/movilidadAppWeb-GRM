/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

/**
 *
 * @author solucionesit
 */
public class OthersIssueDTO {

    private EmployeeDTO empleado;
    private VehicleDTO vehiculo;
    private Integer diasInoperativos;
    private String tipoEvento;
    private String fechaHoraEvento;

    public OthersIssueDTO() {
    }

    public EmployeeDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmployeeDTO empleado) {
        this.empleado = empleado;
    }

    public VehicleDTO getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehicleDTO vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Integer getDiasInoperativos() {
        return diasInoperativos;
    }

    public void setDiasInoperativos(Integer diasInoperativos) {
        this.diasInoperativos = diasInoperativos;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getFechaHoraEvento() {
        return fechaHoraEvento;
    }

    public void setFechaHoraEvento(String fechaHoraEvento) {
        this.fechaHoraEvento = fechaHoraEvento;
    }

}
