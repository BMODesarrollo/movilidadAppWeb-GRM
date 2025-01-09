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
public class StrandedDTO {

    private EmployeeDTO empleado;
    private String nombreSistema;
    private String fechaHoraEvento;
    private VehicleDTO vehiculo;

    public StrandedDTO() {
    }

    public StrandedDTO(EmployeeDTO empleado, String nombreSistema, VehicleDTO vehiculo, String fechaHoraEvento) {
        this.empleado = empleado;
        this.nombreSistema = nombreSistema;
        this.vehiculo = vehiculo;
        this.fechaHoraEvento = fechaHoraEvento;
    }

    public EmployeeDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmployeeDTO empleado) {
        this.empleado = empleado;
    }

    public String getNombreSistema() {
        return nombreSistema;
    }

    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }

    public VehicleDTO getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehicleDTO vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getFechaHoraEvento() {
        return fechaHoraEvento;
    }

    public void setFechaHoraEvento(String fechaHoraEvento) {
        this.fechaHoraEvento = fechaHoraEvento;
    }

}
