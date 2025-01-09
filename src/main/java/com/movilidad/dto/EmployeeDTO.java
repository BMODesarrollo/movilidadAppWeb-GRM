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
public class EmployeeDTO {

    private String nombre;
    private String telefono;
    private String codigoTm;

    public EmployeeDTO(String nombre, String telefono, String codigoTm) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.codigoTm = codigoTm;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }
    
    
}
