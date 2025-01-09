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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "cable_acc_testigo")
@XmlRootElement
public class CableAccTestigo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_testigo")
    private Integer idCableAccTestigo;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 30)
    @Column(name = "num_identificacion")
    private String numIdentificacion;
    @Size(max = 30)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 255)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 255)
    @Column(name = "firma")
    private String firma;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_cable_accidentalidad", referencedColumnName = "id_cable_accidentalidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccidentalidad idCableAccidentalidad;
    @JoinColumn(name = "id_tipo_doc", referencedColumnName = "id_empleado_tipo_identificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoIdentificacion idTipoDoc;
    @JoinColumn(name = "id_acc_profesion", referencedColumnName = "id_acc_profesion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccProfesion idAccProfesion;

    public CableAccTestigo() {
    }

    public CableAccTestigo(Integer idCableAccTestigo) {
        this.idCableAccTestigo = idCableAccTestigo;
    }

    public Integer getIdCableAccTestigo() {
        return idCableAccTestigo;
    }

    public void setIdCableAccTestigo(Integer idCableAccTestigo) {
        this.idCableAccTestigo = idCableAccTestigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public CableAccidentalidad getIdCableAccidentalidad() {
        return idCableAccidentalidad;
    }

    public void setIdCableAccidentalidad(CableAccidentalidad idCableAccidentalidad) {
        this.idCableAccidentalidad = idCableAccidentalidad;
    }

    public EmpleadoTipoIdentificacion getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(EmpleadoTipoIdentificacion idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public AccProfesion getIdAccProfesion() {
        return idAccProfesion;
    }

    public void setIdAccProfesion(AccProfesion idAccProfesion) {
        this.idAccProfesion = idAccProfesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccTestigo != null ? idCableAccTestigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccTestigo)) {
            return false;
        }
        CableAccTestigo other = (CableAccTestigo) object;
        if ((this.idCableAccTestigo == null && other.idCableAccTestigo != null) || (this.idCableAccTestigo != null && !this.idCableAccTestigo.equals(other.idCableAccTestigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccTestigo[ idCableAccTestigo=" + idCableAccTestigo + " ]";
    }

}
