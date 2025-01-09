/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "my_app_cambio_tarea")
@XmlRootElement
public class MyAppCambioTarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_my_app_cambio_tarea")
    private Integer idMyAppCambioTarea;
    @Column(name = "cod_operador")
    private String codOperador;
    @Column(name = "ruta")
    private String ruta;
    @Column(name = "control_tm")
    private String controlTm;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Basic(optional = false)
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Basic(optional = false)
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    // desde Rigel
    @Column(name = "fecha_gestiona")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGestiona;
    @Column(name = "gestionado")
    private Integer gestionado; // 1 para gestionado, 0 para no egstionado.
    @Column(name = "usr_gestiona")
    private String usrGestiona;
    @Lob
    @Column(name = "observacion_gestion")
    private String observacionGestion;

    public MyAppCambioTarea() {
    }

    public MyAppCambioTarea(Integer idMyAppCambioTarea) {
        this.idMyAppCambioTarea = idMyAppCambioTarea;
    }

    public Integer getIdMyAppCambioTarea() {
        return idMyAppCambioTarea;
    }

    public void setIdMyAppCambioTarea(Integer idMyAppCambioTarea) {
        this.idMyAppCambioTarea = idMyAppCambioTarea;
    }

    public String getCodOperador() {
        return codOperador;
    }

    public void setCodOperador(String codOperador) {
        this.codOperador = codOperador;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getControlTm() {
        return controlTm;
    }

    public void setControlTm(String controlTm) {
        this.controlTm = controlTm;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaGestiona() {
        return fechaGestiona;
    }

    public void setFechaGestiona(Date fechaGestiona) {
        this.fechaGestiona = fechaGestiona;
    }

    public Integer getGestionado() {
        return gestionado;
    }

    public void setGestionado(Integer gestionado) {
        this.gestionado = gestionado;
    }

    public String getUsrGestiona() {
        return usrGestiona;
    }

    public void setUsrGestiona(String usrGestiona) {
        this.usrGestiona = usrGestiona;
    }

    public String getObservacionGestion() {
        return observacionGestion;
    }

    public void setObservacionGestion(String observacionGestion) {
        this.observacionGestion = observacionGestion;
    }

}
