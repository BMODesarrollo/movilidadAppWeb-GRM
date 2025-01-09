/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class InsumoProgramacionDTO {

    @Column(name = "id_empleado_tipo_cargo")
    private Integer idEmpleadoTipoCargo;
    private Date fecha;
    private String tipologia;
    @Column(name = "nombre_uf")
    private String nombreUf;
    @Column(name = "total_tareas_prg")
    private long totalTareasPrg;
    @Column(name = "total_reservas_prg")
    private long totalReservasPrg;
    @Column(name = "total_ausentismo")
    private long totalAusentismo;

    public InsumoProgramacionDTO(Integer idEmpleadoTipoCargo, String nombreUf, Date fecha, 
            String tipologia, long totalTareasPrg, long totalReservasPrg, long totalAusentismo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
        this.nombreUf = nombreUf;
        this.fecha = fecha;
        this.tipologia = tipologia;
        this.totalTareasPrg = totalTareasPrg;
        this.totalReservasPrg = totalReservasPrg;
        this.totalAusentismo = totalAusentismo;
    }

    public InsumoProgramacionDTO() {
    }

    public Integer getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(Integer idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    public String getNombreUf() {
        return nombreUf;
    }

    public void setNombreUf(String nombreUf) {
        this.nombreUf = nombreUf;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public long getTotalTareasPrg() {
        return totalTareasPrg;
    }

    public void setTotalTareasPrg(long totalTareasPrg) {
        this.totalTareasPrg = totalTareasPrg;
    }

    public long getTotalReservasPrg() {
        return totalReservasPrg;
    }

    public void setTotalReservasPrg(long totalReservasPrg) {
        this.totalReservasPrg = totalReservasPrg;
    }

    public long getTotalAusentismo() {
        return totalAusentismo;
    }

    public void setTotalAusentismo(long totalAusentismo) {
        this.totalAusentismo = totalAusentismo;
    }

}
