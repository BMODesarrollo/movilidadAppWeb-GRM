/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import com.movilidad.util.beans.CostoLavadoDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "prg_primeras")
@XmlRootElement
public class PrgPrimeras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_primeras")
    private Integer idPrgPrimeras;

    @Column(name = "time_origin")
    private String timeOrigin;
    @Column(name = "ruta")
    private String ruta;
    @Column(name = "parada")
    private String parada;
    @Column(name = "tabla")
    private String tabla;
    @Column(name = "codigo_op")
    private String codigoOp;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "vehiculo")
    private String vehiculo;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    //
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgPrimeras() {
    }

    public PrgPrimeras(Integer idPrgPrimeras, String timeOrigin, String ruta, String parada, String tabla, String codigoOp, String nombres, String apellidos, String vehiculo, Date fecha, GopUnidadFuncional idGopUnidadFuncional) {
        this.idPrgPrimeras = idPrgPrimeras;
        this.timeOrigin = timeOrigin;
        this.ruta = ruta;
        this.parada = parada;
        this.tabla = tabla;
        this.codigoOp = codigoOp;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public Integer getIdPrgPrimeras() {
        return idPrgPrimeras;
    }

    public void setIdPrgPrimeras(Integer idPrgPrimeras) {
        this.idPrgPrimeras = idPrgPrimeras;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getParada() {
        return parada;
    }

    public void setParada(String parada) {
        this.parada = parada;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCodigoOp() {
        return codigoOp;
    }

    public void setCodigoOp(String codigoOp) {
        this.codigoOp = codigoOp;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public String toString() {
        return "PrgPrimeras{" + "idPrgPrimeras=" + idPrgPrimeras + ", timeOrigin=" + timeOrigin + ", ruta=" + ruta + '}';
    }

}
