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
public class AccidentDTO {

    private Integer idAccidente;
    private EmployeeDTO empleado;
    private String ruta;
    private String fechaHoraEvento;
    private VehicleDTO vehiculo;
    private AccDesplazaADTO accDesplazaA;
    private String tipoEvento; // tm16, tm01, tm02
    private boolean asistido;

    public AccidentDTO() {
    }

    public EmployeeDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmployeeDTO empleado) {
        this.empleado = empleado;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public VehicleDTO getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehicleDTO vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public boolean isAsistido() {
        return asistido;
    }

    public void setAsistido(boolean asistido) {
        this.asistido = asistido;
    }

    public String getFechaHoraEvento() {
        return fechaHoraEvento;
    }

    public void setFechaHoraEvento(String fechaHoraEvento) {
        this.fechaHoraEvento = fechaHoraEvento;
    }

    public AccDesplazaADTO getAccDesplazaA() {
        return accDesplazaA;
    }

    public void setAccDesplazaA(AccDesplazaADTO accDesplazaA) {
        this.accDesplazaA = accDesplazaA;
    }

    public Integer getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Integer idAccidente) {
        this.idAccidente = idAccidente;
    }

}
