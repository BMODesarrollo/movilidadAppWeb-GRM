/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.util.List;

/**
 *
 * @author solucionesit
 */
public class ServicesParrillaDTO {

    // operativos
    private Integer totalOperativos;
    // en patio
    private Integer totalEnPatio;
    private List<VehicleDTO> vehiculosEnPatios;
    // asignados
    private Integer totalAsignado;
    private List<VehicleDTO> vehiculosAsignados;

    // inoperativos
    private Integer totalInoperativos;

    // varados
    private Integer totalVarado;
    private List<StrandedDTO> vehiculosVarados;
    // accidente
    private Integer totalAccidente;
    private List<AccidentDTO> vehiculosAccidente;
    // otras novedades de inoperatividad, ni accidente, ni varado
    private Integer totalOtros;
    private List<OthersIssueDTO> vehiculoOtros;

    // mantenimineto
    // mantenimiento en patio
    private Integer totalMttoPatio;
    private List<MantenimentDTO> vehiculosMttoPatio;

    public ServicesParrillaDTO() {
    }

    public Integer getTotalOperativos() {
        return totalOperativos;
    }

    public void setTotalOperativos(Integer totalOperativos) {
        this.totalOperativos = totalOperativos;
    }

    public Integer getTotalEnPatio() {
        return totalEnPatio;
    }

    public void setTotalEnPatio(Integer totalEnPatio) {
        this.totalEnPatio = totalEnPatio;
    }

    public List<VehicleDTO> getVehiculosEnPatios() {
        return vehiculosEnPatios;
    }

    public void setVehiculosEnPatios(List<VehicleDTO> vehiculosEnPatios) {
        this.vehiculosEnPatios = vehiculosEnPatios;
    }

    public Integer getTotalAsignado() {
        return totalAsignado;
    }

    public void setTotalAsignado(Integer totalAsignado) {
        this.totalAsignado = totalAsignado;
    }

    public List<VehicleDTO> getVehiculosAsignados() {
        return vehiculosAsignados;
    }

    public void setVehiculosAsignados(List<VehicleDTO> vehiculosAsignados) {
        this.vehiculosAsignados = vehiculosAsignados;
    }

    public Integer getTotalInoperativos() {
        return totalInoperativos;
    }

    public void setTotalInoperativos(Integer totalInoperativos) {
        this.totalInoperativos = totalInoperativos;
    }

    public Integer getTotalVarado() {
        return totalVarado;
    }

    public void setTotalVarado(Integer totalVarado) {
        this.totalVarado = totalVarado;
    }

    public List<StrandedDTO> getVehiculosVarados() {
        return vehiculosVarados;
    }

    public void setVehiculosVarados(List<StrandedDTO> vehiculosVarados) {
        this.vehiculosVarados = vehiculosVarados;
    }

    public Integer getTotalAccidente() {
        return totalAccidente;
    }

    public void setTotalAccidente(Integer totalAccidente) {
        this.totalAccidente = totalAccidente;
    }

    public List<AccidentDTO> getVehiculosAccidente() {
        return vehiculosAccidente;
    }

    public void setVehiculosAccidente(List<AccidentDTO> vehiculosAccidente) {
        this.vehiculosAccidente = vehiculosAccidente;
    }

    public Integer getTotalOtros() {
        return totalOtros;
    }

    public void setTotalOtros(Integer totalOtros) {
        this.totalOtros = totalOtros;
    }

    public List<OthersIssueDTO> getVehiculoOtros() {
        return vehiculoOtros;
    }

    public void setVehiculoOtros(List<OthersIssueDTO> vehiculoOtros) {
        this.vehiculoOtros = vehiculoOtros;
    }

    public Integer getTotalMttoPatio() {
        return totalMttoPatio;
    }

    public void setTotalMttoPatio(Integer totalMttoPatio) {
        this.totalMttoPatio = totalMttoPatio;
    }

    public List<MantenimentDTO> getVehiculosMttoPatio() {
        return vehiculosMttoPatio;
    }

    public void setVehiculosMttoPatio(List<MantenimentDTO> vehiculosMttoPatio) {
        this.vehiculosMttoPatio = vehiculosMttoPatio;
    }
    
    
    
}
