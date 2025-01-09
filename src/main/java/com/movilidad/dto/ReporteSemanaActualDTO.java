/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadPrgTc;
import com.movilidad.model.ReporteSemanaActualMotivo;
import com.movilidad.model.ReporteSemanaMotivoPrg;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julián Arévalo
 */
@XmlRootElement
public class ReporteSemanaActualDTO extends ServbusPlanificadoDetalleDTO implements Serializable {

    @Column(name = "tabla")
    private Integer tabla;
    
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    
    @Column(name = "id_prg_tc")
    private Integer idPrgTc;
    
    private NovedadPrgTc novedad;
    
    private NovedadPrgTc novedadFinal;
    
    private ReporteSemanaActualMotivo idMotivo; 
    
    private ReporteSemanaMotivoPrg motivoPrg; 

    public ReporteSemanaActualDTO(Date fecha, String servbus, String tipologia, String nombre, String codigoTm, String timeOrigin, String timeDestiny, String codigoBus, String unidadFuncional, String tipoTarea, Long km, String estadoOperacion, Integer tabla, Integer idEmpleado, Integer idPrgTc) {
        super(fecha, servbus, tipologia, nombre, codigoTm, timeOrigin, timeDestiny, codigoBus, unidadFuncional, tipoTarea, km, estadoOperacion);
        this.tabla = tabla;
        this.idEmpleado = idEmpleado;
        this.idPrgTc = idPrgTc;
    }

    public Integer getTabla() {
        return tabla;
    }

    public void setTabla(Integer tabla) {
        this.tabla = tabla;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(Integer idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    public ReporteSemanaActualMotivo getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(ReporteSemanaActualMotivo idMotivo) {
        this.idMotivo = idMotivo;
    }

    public NovedadPrgTc getNovedad() {
        return novedad;
    }

    public void setNovedad(NovedadPrgTc novedad) {
        this.novedad = novedad;
    }

    public NovedadPrgTc getNovedadFinal() {
        return novedadFinal;
    }

    public void setNovedadFinal(NovedadPrgTc novedadFinal) {
        this.novedadFinal = novedadFinal;
    }

    public ReporteSemanaMotivoPrg getMotivoPrg() {
        return motivoPrg;
    }

    public void setMotivoPrg(ReporteSemanaMotivoPrg motivoPrg) {
        this.motivoPrg = motivoPrg;
    }
           
}
